package com.example.zmart.clientproducer.serviceMapper;

import com.example.zmart.clientproducer.dto.ClientRequest;
import com.example.zmart.clientproducer.dto.ClientResponse;
import com.example.zmart.clientproducer.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    Client clientRequestToClient(ClientRequest clientRequest);

    ClientResponse clientToClientResponse(Client client);
}
