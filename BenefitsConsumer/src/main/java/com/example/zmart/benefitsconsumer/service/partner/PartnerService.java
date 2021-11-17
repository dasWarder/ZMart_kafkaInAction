package com.example.zmart.benefitsconsumer.service.partner;

import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.model.bonus.Bonus;
import com.example.zmart.benefitsconsumer.model.partner.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {

    Partner savePartner(Partner partner);

    Partner updatePartnerById(Long id, Partner partner) throws PartnerNotFoundException;

    Partner getPartnerById(Long id) throws PartnerNotFoundException;

    void deletePartnerById(Long id);

    Page<Partner> getPartners(Pageable pageable);

    List<Partner> getPartners();

    Bonus addBonusToPartnerById(Bonus bonus, Long id) throws PartnerNotFoundException, BonusNotFoundException;
}
