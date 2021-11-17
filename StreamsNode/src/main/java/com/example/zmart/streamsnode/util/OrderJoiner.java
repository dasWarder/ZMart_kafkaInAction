package com.example.zmart.streamsnode.util;

import com.example.zmart.streamsnode.mapper.CustomStringObjectMapper;
import com.example.zmart.streamsnode.model.CorrelatedOrder;
import com.example.zmart.streamsnode.model.Order;
import com.example.zmart.streamsnode.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueJoiner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class OrderJoiner implements ValueJoiner<String, String, String> {

  private final CustomStringObjectMapper objectMapper;

  @Override
  public String apply(String s, String s2) {

    log.info("Apply two orders to one OrderJoiner object");

    Order firstOrder = (Order) objectMapper.stringToObject(s, Order.class);
    Order secondOrder = (Order) objectMapper.stringToObject(s2, Order.class);
    CorrelatedOrder correlatedOrder = joinOrdersToCorrelatedOrder(firstOrder, secondOrder);

    String response = objectMapper.objectToString(correlatedOrder);

    return response;
  }

  private CorrelatedOrder joinOrdersToCorrelatedOrder(Order firstOrder, Order secondOrder) {

    CorrelatedOrder.CorrelatedOrderBuilder builder = CorrelatedOrder.builder();

    LocalDateTime firstPurchaseDate = firstOrder.getPurchaseDate();
    Double firstCostOfPurchase = firstOrder.getCostOfPurchase();
    Collection<Product> firstProducts = firstOrder.getProducts();

    LocalDateTime secondPurchaseDate = secondOrder.getPurchaseDate();
    Double secondCostOfPurchase = secondOrder.getCostOfPurchase();
    Collection<Product> secondProducts = secondOrder.getProducts();

    List<Product> products = new ArrayList<>();

    Stream.of(firstProducts, secondProducts)
        .flatMap(ps -> ps.stream())
        .forEach(p -> products.add(p));

    String firstCustomerId = firstOrder.getId();
    String secondCustomerId = secondOrder.getId();

    CorrelatedOrder responsePurchase =
        builder
            .id(firstCustomerId != null ? firstCustomerId : secondCustomerId)
            .firstPurchaseDate(firstPurchaseDate)
            .secondPurchaseDate(secondPurchaseDate)
            .purchasedItem(products)
            .totalAmount(firstCostOfPurchase + secondCostOfPurchase)
            .build();

    return responsePurchase;
  }
}
