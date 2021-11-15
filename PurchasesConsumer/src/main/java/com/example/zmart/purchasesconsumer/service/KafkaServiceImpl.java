package com.example.zmart.purchasesconsumer.service;


import com.example.zmart.purchasesconsumer.model.Order;
import com.example.zmart.purchasesconsumer.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "purchases", groupId = "purchases_group_id")
    public void consume(String serializedProduct) throws JsonProcessingException {

        log.info("Consume a serialized object as a string");
        Order order = objectMapper.readValue(serializedProduct, Order.class);
        log.info("PRODUCT RECEIVED: {}", order.toString());

    }
}
