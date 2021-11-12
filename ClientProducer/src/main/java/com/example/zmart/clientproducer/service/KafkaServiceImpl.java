package com.example.zmart.clientproducer.service;

import com.example.zmart.clientproducer.dto.PartnerBonusesRequest;
import com.example.zmart.clientproducer.dto.PartnerBonusesResponse;
import com.example.zmart.clientproducer.exception.ClientNotFoundException;
import com.example.zmart.clientproducer.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

  private final ObjectMapper objectMapper;

  private final ClientService clientService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  @KafkaListener(topics = "clients_data", groupId = "benefits_group_1")
  public void consume(String partnerBonusesRequest)
      throws ClientNotFoundException, JsonProcessingException {

    log.info("Received partner bonuses object = {}", partnerBonusesRequest);

    PartnerBonusesRequest request =
        objectMapper.readValue(partnerBonusesRequest, PartnerBonusesRequest.class);
    Client clientByClientId = clientService.getClientByClientId(request.getClientId());

    PartnerBonusesResponse partnerBonusesResponse =
        PartnerBonusesResponse.builder()
            .clientEmail(clientByClientId.getEmail())
            .bonuses(request.getBonuses())
            .build();
    String serializedResponse = objectMapper.writeValueAsString(partnerBonusesResponse);

    kafkaTemplate.send("users", serializedResponse);
  }
}
