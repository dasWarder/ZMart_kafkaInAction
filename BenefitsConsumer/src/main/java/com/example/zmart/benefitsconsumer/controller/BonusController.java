package com.example.zmart.benefitsconsumer.controller;

import com.example.zmart.benefitsconsumer.dto.bonus.BonusRequest;
import com.example.zmart.benefitsconsumer.dto.bonus.BonusResponse;
import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.mappingService.bonus.BonusMapper;
import com.example.zmart.benefitsconsumer.model.Bonus;
import com.example.zmart.benefitsconsumer.service.BonusService;
import com.example.zmart.benefitsconsumer.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners/{partnerId}/bonuses")
public class BonusController {

  private final BonusMapper bonusMapper;

  private final PartnerService partnerService;

  private final BonusService bonusService;

  @GetMapping("/{bonusId}")
  public ResponseEntity<BonusResponse> getBonusByIdAndPartnerId(
          @PathVariable("partnerId") @NotNull @Min(1) Long partnerId, @PathVariable("bonusId") @NotNull @Min(1) Long bonusId)
      throws BonusNotFoundException {

    Bonus bonus = bonusService.getBonusByIdAndPartnerId(bonusId, partnerId);
    BonusResponse responseDto = bonusMapper.bonusToBonusResponse(bonus);

    return ResponseEntity.ok(responseDto);
  }

  @PostMapping
  public ResponseEntity<BonusResponse> addBonusToPartner(
          @RequestBody @NotNull @Valid BonusRequest bonusRequest, @PathVariable("partnerId") @NotNull @Min(1) Long partnerId)
      throws PartnerNotFoundException, BonusNotFoundException {

    Bonus bonus = bonusMapper.bonusRequestToBonus(bonusRequest);
    Bonus storedBonus = partnerService.addBonusToPartnerById(bonus, partnerId);
    BonusResponse responseDto = bonusMapper.bonusToBonusResponse(storedBonus);

    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<List<BonusResponse>> getBonusesByPartnerId(
      @PathVariable("partnerId") @NotNull @Min(1) Long partnerId) {

    List<BonusResponse> bonuses =
        bonusService.getBonusesByPartnerId(partnerId).stream()
            .map(bonusMapper::bonusToBonusResponse)
            .collect(Collectors.toList());

    return ResponseEntity.ok(bonuses);
  }

  @GetMapping("/points/{points}")
  public ResponseEntity<List<BonusResponse>> getBonusesByPartnerId(
      @PathVariable("partnerId") @NotNull @Min(1) Long partnerId, @PathVariable("points") @NotNull @Min(1) Long points) {

    List<BonusResponse> bonuses =
        bonusService.getBonusesByRewardPointAndPartnerId(points, partnerId).stream()
            .map(bonusMapper::bonusToBonusResponse)
            .collect(Collectors.toList());

    return ResponseEntity.ok(bonuses);
  }

  @DeleteMapping("/{bonusId}")
  public ResponseEntity<Void> deleteBonusByIdAndPartnerId(
      @PathVariable("partnerId") @NotNull @Min(1) Long partnerId, @PathVariable("bonusId") @NotNull @Min(1) Long bonusId) {

    bonusService.deleteBonusByIdAndPartnerId(bonusId, partnerId);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{bonusId}")
  public ResponseEntity<BonusResponse> updateBonusByIdAndPartnerId(
      @PathVariable("partnerId") @NotNull @Min(1) Long partnerId,
      @PathVariable("bonusId") @NotNull @Min(1) Long bonusId,
      @RequestBody @NotNull @Valid BonusRequest bonusRequest)
      throws BonusNotFoundException, PartnerNotFoundException {

    Bonus bonus = bonusMapper.bonusRequestToBonus(bonusRequest);
    Bonus updatedBonus = bonusService.updateBonusByBonusIdAndPartnerId(bonusId, partnerId, bonus);
    BonusResponse responseDto = bonusMapper.bonusToBonusResponse(updatedBonus);

    return ResponseEntity.ok(responseDto);
  }
}
