package com.example.zmart.benefitsconsumer.service.bonus;

import com.example.zmart.benefitsconsumer.exception.BonusNotFoundException;
import com.example.zmart.benefitsconsumer.exception.PartnerNotFoundException;
import com.example.zmart.benefitsconsumer.model.bonus.Bonus;

import java.util.List;

public interface BonusService {

    Bonus saveBonusForPartner(Bonus bonus, Long id) throws PartnerNotFoundException;

    void deleteBonusByIdAndPartnerId(Long bonusId, Long partnerId);

    Bonus getBonusByIdAndPartnerId(Long bonusId, Long partnerId) throws BonusNotFoundException;

    Bonus updateBonusByBonusIdAndPartnerId(Long bonusId, Long partnerId, Bonus bonus) throws PartnerNotFoundException, BonusNotFoundException;

    List<Bonus> getBonusesByRewardPointAndPartnerId(Long rewardPoints, Long id);

    List<Bonus> getBonusesByPartnerId(Long id);
}
