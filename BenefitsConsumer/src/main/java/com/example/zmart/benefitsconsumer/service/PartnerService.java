package com.example.zmart.benefitsconsumer.service;

import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.model.Bonus;
import com.example.zmart.benefitsconsumer.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {

    Partner savePartner(Partner partner);

    Partner updatePartnerById(Long id, Partner partner) throws PartnerNotFoundException;

    Partner getPartnerById(Long id) throws PartnerNotFoundException;

    void deletePartnerById(Long id);

    Page<Partner> getPartners(Pageable pageable);

    Bonus addBonusToPartnerById(Bonus bonus, Long id) throws PartnerNotFoundException, BonusNotFoundException;
}
