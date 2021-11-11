package com.example.zmart.benefitsconsumer.service;

import com.example.zmart.benefitsconsumer.dao.PartnerRepository;
import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.model.Bonus;
import com.example.zmart.benefitsconsumer.model.Partner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

  private final BonusService bonusService;

  private final PartnerRepository partnerRepository;

  @Override
  @Transactional
  public Partner savePartner(Partner partner) {

    log.info("Store a new partner");

    partner.setBonuses(new HashSet<>());

    return partnerRepository.save(partner);
  }

  @Override
  @Transactional
  public Partner updatePartnerById(Long id, Partner partner) throws PartnerNotFoundException {

    log.info("Update a partner with ID = {}", id);

    Partner partnerById = this.getPartnerById(id);
    partner.setId(partnerById.getId());
    partner.setBonuses(partnerById.getBonuses());

    return partnerRepository.save(partner);
  }

  @Override
  @Transactional(readOnly = true)
  public Partner getPartnerById(Long id) throws PartnerNotFoundException {

    log.info("Fetch a partner by ID = {}", id);

    return partnerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new PartnerNotFoundException(String.format("Partner with id = %d not found", id)));
  }

  @Override
  @Transactional
  public void deletePartnerById(Long id) {

    log.info("Remove a partner by ID = {}", id);

    partnerRepository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Partner> getPartners(Pageable pageable) {

    log.info("Get a collection of partners");

    return partnerRepository.findAll(pageable);
  }

  @Override
  @Transactional
  public Bonus addBonusToPartnerById(Bonus bonus, Long id)
      throws PartnerNotFoundException {

    log.info("Add bonus to the partner with ID = {}", id);

    Partner partnerById = this.getPartnerById(id);
    bonus.setPartner(partnerById);
    Bonus bonusWithId = bonusService.saveBonusForPartner(bonus, partnerById.getId());

    return bonusWithId;
  }
}
