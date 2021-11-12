package com.example.zmart.clientproducer.service;

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
    public void consume(String clientId) throws ClientNotFoundException, JsonProcessingException {

        log.info("Received client id = {}", clientId);
        Client clientByClientId = clientService.getClientByClientId(clientId);
        String serializedClient = objectMapper.writeValueAsString(clientByClientId);
        kafkaTemplate.send("users", serializedClient);
    }
}
