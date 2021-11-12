package com.example.zmart.clientproducer.dao;

import com.example.zmart.clientproducer.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Optional<Client> getClientByClientId(String clientId);

    void deleteClientByClientId(String clientId);
}
