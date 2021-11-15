package com.example.zmart.purchasesconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaService {

    void consume(String serializedProduct) throws JsonProcessingException;
}
