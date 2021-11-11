package com.example.zmart.benefitsconsumer.service;

import com.example.zmart.benefitsconsumer.dao.BonusRepository;
import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.model.Bonus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

  private final BonusRepository bonusRepository;

  @Override
  @Transactional
  public Bonus saveBonusForPartner(Bonus bonus, Long id){

    log.info("Store a bonus for the partner with ID = {}", id);

    return bonusRepository.save(bonus);
  }

  @Override
  @Transactional
  public void deleteBonusByIdAndPartnerId(Long bonusId, Long partnerId) {

    log.info("Remove a bonus by ID = {} and partner ID = {}", bonusId, partnerId);

    bonusRepository.deleteBonusByIdAndPartnerId(bonusId, partnerId);
  }

  @Override
  @Transactional(readOnly = true)
  public Bonus getBonusByIdAndPartnerId(Long bonusId, Long partnerId)
      throws BonusNotFoundException {

    log.info("Fetch bonus by ID = {} and partner ID = {}", bonusId, partnerId);

    return bonusRepository
        .getBonusByIdAndPartnerId(bonusId, partnerId)
        .orElseThrow(
            () ->
                new BonusNotFoundException(
                    String.format(
                        "Bonus with id = %d and partner id = %d not found", bonusId, partnerId)));
  }

  @Override
  @Transactional
  public Bonus updateBonusByBonusIdAndPartnerId(Long bonusId, Long partnerId, Bonus bonus)
      throws BonusNotFoundException {

    log.info("Update a bonus by its ID = {} and partner ID = {}", bonusId, partnerId);

    Bonus dbBonus =
        bonusRepository
            .getBonusByIdAndPartnerId(bonusId, partnerId)
            .orElseThrow(
                () ->
                    new BonusNotFoundException(
                        String.format(
                            "Bonus with id = %d and partner id = %d not found",
                            bonusId, partnerId)));
    bonus.setId(dbBonus.getId());
    bonus.setPartner(dbBonus.getPartner());

    return bonusRepository.save(bonus);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Bonus> getBonusesByRewardPointAndPartnerId(Long rewardPoints, Long id) {

    log.info("Fetch bonuses for {} reward points by partner ID = {}", rewardPoints, id);

    return bonusRepository.getBonusesByRewardPointsBeforeAndPartnerId(rewardPoints, id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Bonus> getBonusesByPartnerId(Long id) {

    log.info("Fetch bonuses by partner ID = {}", id);

    return bonusRepository.getBonusesByPartnerId(id);
  }
}
