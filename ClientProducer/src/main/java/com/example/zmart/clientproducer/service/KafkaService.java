package com.example.zmart.clientproducer.service;

import com.example.zmart.clientproducer.exception.ClientNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaService {

    void consume(String clientId) throws ClientNotFoundException, JsonProcessingException;
}
