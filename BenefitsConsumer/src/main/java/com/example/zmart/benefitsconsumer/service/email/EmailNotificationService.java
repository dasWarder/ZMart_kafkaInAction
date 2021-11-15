package com.example.zmart.benefitsconsumer.service.email;

import com.example.zmart.benefitsconsumer.dto.info.PartnerBonusesResponse;

public interface EmailNotificationService {

    void sendBenefits(PartnerBonusesResponse response);
}
