package com.example.zmart.clientproducer.service;

import com.example.zmart.clientproducer.exception.ClientNotFoundException;
import com.example.zmart.clientproducer.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    Client saveClient(Client client);

    Client updateClient(String clientId, Client client) throws ClientNotFoundException;

    Client getClientByClientId(String clientId) throws ClientNotFoundException;

    void deleteClientByClientId(String clientId);

    Page<Client> getClients(Pageable pageable);
}
