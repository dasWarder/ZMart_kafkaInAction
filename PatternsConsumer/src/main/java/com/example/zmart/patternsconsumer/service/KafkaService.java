package com.example.zmart.patternsconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaService {

    void consume(String serializedObject) throws JsonProcessingException;
}
