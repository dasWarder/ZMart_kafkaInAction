package com.example.zmart.clientproducer.controller;

import com.example.zmart.clientproducer.dto.ClientRequest;
import com.example.zmart.clientproducer.dto.ClientResponse;
import com.example.zmart.clientproducer.exception.ClientNotFoundException;
import com.example.zmart.clientproducer.model.Client;
import com.example.zmart.clientproducer.service.ClientService;
import com.example.zmart.clientproducer.serviceMapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

  private final ClientMapper clientMapper;

  private final ClientService clientService;

  @PostMapping
  public ResponseEntity<ClientResponse> saveClient(@RequestBody ClientRequest clientRequest) {

    Client client = clientMapper.clientRequestToClient(clientRequest);
    Client storedClient = clientService.saveClient(client);
    ClientResponse responseDto = clientMapper.clientToClientResponse(storedClient);

    return ResponseEntity.created(URI.create("http://localhost:9092/api/v1/clients"))
        .body(responseDto);
  }

  @GetMapping("/{clientId}")
  public ResponseEntity<ClientResponse> getClientByClientId(
      @PathVariable("clientId") String clientId) throws ClientNotFoundException {

    Client clientByClientId = clientService.getClientByClientId(clientId);
    ClientResponse responseDto = clientMapper.clientToClientResponse(clientByClientId);

    return ResponseEntity.ok(responseDto);
  }

  @PutMapping
  public ResponseEntity<ClientResponse> updateClientByClientId(
      @RequestParam("clientId") String clientId, @RequestBody Client updatingClient)
      throws ClientNotFoundException {

    Client updatedClient = clientService.updateClient(clientId, updatingClient);
    ClientResponse responseDto = clientMapper.clientToClientResponse(updatedClient);

    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteClientByClientId(@RequestParam String clientId) {

    clientService.deleteClientByClientId(clientId);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<ClientResponse>> getClients(
      @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

    Page<ClientResponse> responsePage =
        clientService.getClients(pageable).map(clientMapper::clientToClientResponse);

    return ResponseEntity.ok(responsePage);
  }
}
