package com.example.zmart.streamsnode.util;

import com.example.zmart.streamsnode.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.ZoneOffset;

public class TransactionTimestampExtractor implements TimestampExtractor {

  @Override
  public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {

    Order order = (Order) consumerRecord.value();

    return order.getPurchaseDate().toEpochSecond(ZoneOffset.UTC);
  }
}
