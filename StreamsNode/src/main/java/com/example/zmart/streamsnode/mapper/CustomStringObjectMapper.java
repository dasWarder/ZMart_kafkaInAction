package com.example.zmart.streamsnode.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomStringObjectMapper {

  private final ObjectMapper objectMapper;

  public <T> Object stringToObject(String serializeString, Class aClass) {

    try {
      return objectMapper.readValue(serializeString, aClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public <T> String objectToString(T obj) {

    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
