package com.example.zmart.benefitsconsumer.controller;

import com.example.zmart.benefitsconsumer.dto.partner.PartnerRequest;
import com.example.zmart.benefitsconsumer.dto.partner.PartnerResponse;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.mappingService.partner.PartnerMapper;
import com.example.zmart.benefitsconsumer.model.Partner;
import com.example.zmart.benefitsconsumer.service.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerController {

  private final PartnerMapper partnerMapper;

  private final PartnerService partnerService;

  @PostMapping
  public ResponseEntity<PartnerResponse> savePartner(
      @RequestBody @Valid @NotNull PartnerRequest partnerRequest) {

    Partner partner = partnerMapper.partnerRequestToPartner(partnerRequest);
    Partner storedPartner = partnerService.savePartner(partner);
    PartnerResponse responseDto = partnerMapper.partnerToPartnerResponse(storedPartner);

    return ResponseEntity.created(URI.create("http://localhost:8080/api/v1/partners"))
        .body(responseDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PartnerResponse> getPartnerById(
      @PathVariable("id") @NotNull @Min(1) Long id) throws PartnerNotFoundException {

    Partner partnerById = partnerService.getPartnerById(id);
    PartnerResponse responseDto = partnerMapper.partnerToPartnerResponse(partnerById);

    return ResponseEntity.ok(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PartnerResponse> updatePartner(
      @PathVariable("id") @NotNull @Min(1) Long id,
      @RequestBody @NotNull @Valid PartnerRequest partnerRequest)
      throws PartnerNotFoundException {

    Partner partner = partnerMapper.partnerRequestToPartner(partnerRequest);
    Partner updatedPartner = partnerService.updatePartnerById(id, partner);
    PartnerResponse responseDto = partnerMapper.partnerToPartnerResponse(updatedPartner);

    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePartnerById(@PathVariable("id") @NotNull @Min(1) Long id) {

    partnerService.deletePartnerById(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<PartnerResponse>> getPartners(
      @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC)
          Pageable pageable) {

    Page<PartnerResponse> responsePage =
        partnerService.getPartners(pageable).map(partnerMapper::partnerToPartnerResponse);

    return ResponseEntity.ok(responsePage);
  }
}
