package com.example.zmart.patternsconsumer.service;

import com.example.zmart.patternsconsumer.dto.PatternsInfoRequest;
import com.example.zmart.patternsconsumer.model.PatternsInfo;
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
  @KafkaListener(topics = "patterns", groupId = "patterns_group_id")
  public void consume(String serializedObject) throws JsonProcessingException {

    log.info("Consume a serialized object as a string from the topic \\\\'patterns\\\\'");
    PatternsInfoRequest patternsInfoRequest =
        objectMapper.readValue(serializedObject, PatternsInfoRequest.class);

    PatternsInfo patternsInfo = patternsInfoRequestToPatternsInfo(patternsInfoRequest);
    log.info("Received pattern: {}", patternsInfo.toString());
  }

  private PatternsInfo patternsInfoRequestToPatternsInfo(PatternsInfoRequest dto) {

    PatternsInfo patternsInfo =
        PatternsInfo.builder().zip(dto.getZip()).products(dto.getProducts()).build();

    return patternsInfo;
  }
}
