package com.example.zmart.streamsnode.util;

import com.example.zmart.streamsnode.mapper.CustomStringObjectMapper;
import com.example.zmart.streamsnode.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.StreamPartitioner;

@Slf4j
@RequiredArgsConstructor
public class BenefitsStreamPartitioner implements StreamPartitioner<String, String> {

    private final CustomStringObjectMapper stringObjectMapper;

    @Override
    public Integer partition(String s, String s2, String order, int i) {

        Order orderObj = (Order) stringObjectMapper.stringToObject(order, Order.class);
        log.info("Order object partition {} based on hash code", Math.abs(orderObj.getId().hashCode() % i));

        return Math.abs(orderObj.getId().hashCode() % i);
    }
}
