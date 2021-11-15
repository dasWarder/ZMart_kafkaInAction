package com.example.zmart.benefitsconsumer.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumeService {

    void consume(String benefitsInfo) throws JsonProcessingException;


}
