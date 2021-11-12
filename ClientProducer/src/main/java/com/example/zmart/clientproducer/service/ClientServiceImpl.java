package com.example.zmart.clientproducer.service;

import com.example.zmart.clientproducer.dao.ClientRepository;
import com.example.zmart.clientproducer.exception.ClientNotFoundException;
import com.example.zmart.clientproducer.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  @Override
  @Transactional
  public Client saveClient(Client client) {

    log.info("Store a new client");
    client.setClientId(UUID.randomUUID().toString());

    return clientRepository.save(client);
  }

  @Override
  @Transactional
  public Client updateClient(String clientId, Client client) throws ClientNotFoundException {

    log.info("Update a client with clientId = {}", clientId);

    Client clientByClientId = this.getClientByClientId(clientId);
    client.setClientId(clientByClientId.getClientId());
    client.setId(clientByClientId.getId());

    return clientRepository.save(client);
  }

  @Override
  @Transactional(readOnly = true)
  public Client getClientByClientId(String clientId) throws ClientNotFoundException {

    log.info("Fetch a client by ID ={}", clientId);

    return clientRepository
        .getClientByClientId(clientId)
        .orElseThrow(
            () ->
                new ClientNotFoundException(
                    String.format("A client with ID = %s not found", clientId)));
  }

  @Override
  @Transactional
  public void deleteClientByClientId(String clientId) {

    log.info("Remove a client with clientID = {}", clientId);

    clientRepository.deleteClientByClientId(clientId);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Client> getClients(Pageable pageable) {

    log.info("Fetch client's page");

    return clientRepository.findAll(pageable);
  }
}
