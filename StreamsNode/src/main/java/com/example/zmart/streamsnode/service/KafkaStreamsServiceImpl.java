package com.example.zmart.streamsnode.service;

import com.example.zmart.streamsnode.mapper.CustomStringObjectMapper;
import com.example.zmart.streamsnode.mapper.OrderMapper;
import com.example.zmart.streamsnode.model.Order;
import com.example.zmart.streamsnode.util.BenefitsStreamPartitioner;
import com.example.zmart.streamsnode.util.CustomBenefitsTransformer;
import com.example.zmart.streamsnode.util.StreamsUtil;
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

import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaStreamsServiceImpl implements KafkaStreamsService {

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
        builder.stream(sourceTopic, Consumed.with(Serdes.String(), Serdes.String()));

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

    log.info("Processing cloaking a card number stream");

    KStream<String, Order> ordersStream =
        sourceStream.mapValues((k, v) -> (Order) stringObjectMapper.stringToObject(v, Order.class));
    ordersStream.foreach((k, v) -> v.setCardNumber(StreamsUtil.cloakCardNumber(v.getCardNumber())));

    return ordersStream;
  }

  private void processingToPurchasesStock(KStream<String, Order> stream) {

    log.info("Processing orders with a cost greater that 5$ to the purchase stock");

    KeyValueMapper<String, Order, Long> epochMilliAsAKeyMapper =
        (k, v) -> v.getPurchaseDate().toInstant(ZoneOffset.UTC).toEpochMilli();

    stream
        .filter((k, v) -> v.getCostOfPurchase() > 5.0)
        .selectKey(epochMilliAsAKeyMapper)
        .mapValues(stringObjectMapper::objectToString)
        .to(purchasesTransactionsTopic, Produced.with(Serdes.Long(), Serdes.String()));
  }

  private void processingToBenefitsStock(KStream<String, Order> stream, StreamsBuilder builder) {

    log.info("Processing orders to the benefits stock");

    StoreBuilder<KeyValueStore<String, String>> benefits =
        Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore("benefits"), Serdes.String(), Serdes.String());

    builder.addStateStore(benefits);

    KStream<String, String> subtopicStream = stream
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

    log.info("Processing orders to the patterns stock");

    stream
        .mapValues(orderMapper::orderToPatternsInfo)
        .mapValues(stringObjectMapper::objectToString)
        .to(patternsTopic, Produced.with(Serdes.String(), Serdes.String()));
  }

  private void processingCoffeeAndElectronicBranching(KStream<String, Order> stream) {

    log.info("Processing orders branching to the caffe and the electronic stocks");

    Predicate<String, Order> coffeeBranch = (k, v) -> v.getDepartment().getName().equals("coffee");
    Predicate<String, Order> electronicBranch =
        (k, v) -> v.getDepartment().getName().equals("electronic");

    KStream<String, Order>[] newBranches = stream.branch(coffeeBranch, electronicBranch);

    newBranches[0]
        .mapValues(stringObjectMapper::objectToString)
        .to(coffeeDepartmentTopic, Produced.with(Serdes.String(), Serdes.String()));
    newBranches[1]
        .mapValues(stringObjectMapper::objectToString)
        .to(electronicDepartmentTopic, Produced.with(Serdes.String(), Serdes.String()));
  }

  /*
  Integer currentPurchaseBonuses = (int) Math.round(order.getCostOfPurchase());

        benefitsInfo = orderMapper.orderToBenefitsInfo(order);
        benefitsInfo.setCurrentRewardPoints(currentPurchaseBonuses);

        Integer bonuses = stateStore.get(benefitsInfo.getClientId());

        if( Objects.nonNull(bonuses)) {
            benefitsInfo.setTotalRewardPoints(benefitsInfo.getCurrentRewardPoints() + bonuses);
        }

        stateStore.put(benefitsInfo.getClientId(), benefitsInfo.getTotalRewardPoints());

        return KeyValue.pair();
   */
}
