package com.example.zmart.streamsnode.service;

import com.example.zmart.streamsnode.mapper.CustomStringObjectMapper;
import com.example.zmart.streamsnode.mapper.OrderMapper;
import com.example.zmart.streamsnode.model.CorrelatedOrder;
import com.example.zmart.streamsnode.model.Department;
import com.example.zmart.streamsnode.model.Order;
import com.example.zmart.streamsnode.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaStreamsServiceImpl implements KafkaStreamsService {

  private final Integer COFFEE_PURCHASE = 0;
  private final Integer ELECTRONIC_PURCHASE = 1;

  @Value("${kafka.topics.subtopic}")
  private String subtopic;

  @Value("${kafka.topics.source}")
  private String sourceTopic;

  @Value("${kafka.topics.benefits-stock}")
  private String benefitsTopic;

  @Value("${kafka.topics.patterns-stock}")
  private String patternsTopic;

  @Value("${kafka.topics.purchases-transactions}")
  private String purchasesTransactionsTopic;

  @Value("${kafka.topics.coffee-department}")
  private String coffeeDepartmentTopic;

  @Value("${kafka.topics.electronic-department}")
  private String electronicDepartmentTopic;

  private final OrderMapper orderMapper;

  private final CustomStringObjectMapper stringObjectMapper;

  @Bean
  @Override
  public KStream<String, String> processingStream(StreamsBuilder builder) {

    KStream<String, String> sourceStream =
        builder.stream(
            sourceTopic,
            Consumed.with(Serdes.String(), Serdes.String())
                .withTimestampExtractor(new TransactionTimestampExtractor()));

    KStream<String, Order> cloakingCardNumberStream =
        creatingCloakingCardNumberStream(sourceStream);

    processingToBenefitsStock(cloakingCardNumberStream, builder);
    processingToPurchasesStock(cloakingCardNumberStream);
    processingPatternsStock(cloakingCardNumberStream);
    processingCoffeeAndElectronicBranching(cloakingCardNumberStream);

    return sourceStream;
  }

  private KStream<String, Order> creatingCloakingCardNumberStream(
      KStream<String, String> sourceStream) {

    KStream<String, Order> ordersStream =
        sourceStream.mapValues((k, v) -> (Order) stringObjectMapper.stringToObject(v, Order.class));
    ordersStream.foreach((k, v) -> v.setCardNumber(StreamsUtil.cloakCardNumber(v.getCardNumber())));

    return ordersStream;
  }

  private void processingToPurchasesStock(KStream<String, Order> stream) {

    KeyValueMapper<String, Order, Long> epochMilliAsAKeyMapper =
        (k, v) -> v.getPurchaseDate().toInstant(ZoneOffset.UTC).toEpochMilli();

    stream
        .filter((k, v) -> v.getCostOfPurchase() > 5.0)
        .selectKey(epochMilliAsAKeyMapper)
        .mapValues(stringObjectMapper::objectToString)
        .to(purchasesTransactionsTopic, Produced.with(Serdes.Long(), Serdes.String()));
  }

  private void processingToBenefitsStock(KStream<String, Order> stream, StreamsBuilder builder) {

    StoreBuilder<KeyValueStore<String, String>> benefits =
        Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore("benefits"), Serdes.String(), Serdes.String());

    builder.addStateStore(benefits);

    KStream<String, String> subtopicStream =
        stream
            .mapValues(stringObjectMapper::objectToString)
            .through(
                subtopic,
                Produced.with(
                    Serdes.String(),
                    Serdes.String(),
                    new BenefitsStreamPartitioner(stringObjectMapper)));

    subtopicStream
        .transformValues(
            new CustomBenefitsTransformer(benefits.name(), orderMapper, stringObjectMapper),
            benefits.name())
        .to(
            benefitsTopic,
            (Produced<String, String>) Produced.with(Serdes.String(), Serdes.String()));
  }

  private void processingPatternsStock(KStream<String, Order> stream) {

    stream
        .mapValues(orderMapper::orderToPatternsInfo)
        .mapValues(stringObjectMapper::objectToString)
        .to(patternsTopic, Produced.with(Serdes.String(), Serdes.String()));
  }

  private void processingCoffeeAndElectronicBranching(KStream<String, Order> stream) {

    Predicate<String, Order> coffeeBranch = (k, v) -> v.getDepartment().equals(Department.COFFEE);
    Predicate<String, Order> electronicBranch =
        (k, v) -> v.getDepartment().equals(Department.ELECTRONIC);

    KStream<String, Order>[] newBranches = stream.branch(coffeeBranch, electronicBranch);

    KStream<String, String> coffeeStream =
        newBranches[COFFEE_PURCHASE].mapValues(stringObjectMapper::objectToString);

    KStream<String, String> electronicStream =
        newBranches[ELECTRONIC_PURCHASE].mapValues(stringObjectMapper::objectToString);

    ValueJoiner<String, String, String> orderJoiner = new OrderJoiner(stringObjectMapper);
    JoinWindows joinWindows = JoinWindows.of(Duration.ofMinutes(20));

    coffeeStream
        .join(electronicStream, orderJoiner, joinWindows)
        .to(
            "correlatedOrder",
            (Produced<String, String>) Produced.with(Serdes.String(), Serdes.String()));

    coffeeStream.to(
        coffeeDepartmentTopic,
        (Produced<String, String>) Produced.with(Serdes.String(), Serdes.String()));

    electronicStream.to(
        electronicDepartmentTopic,
        (Produced<String, String>) Produced.with(Serdes.String(), Serdes.String()));
  }
}
