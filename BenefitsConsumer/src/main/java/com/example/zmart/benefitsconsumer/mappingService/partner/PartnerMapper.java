package com.example.zmart.benefitsconsumer.mappingService.partner;

import com.example.zmart.benefitsconsumer.dto.partner.PartnerRequest;
import com.example.zmart.benefitsconsumer.dto.partner.PartnerResponse;
import com.example.zmart.benefitsconsumer.mappingService.bonus.BonusMapper;
import com.example.zmart.benefitsconsumer.model.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = BonusMapper.class)
public interface PartnerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    Partner partnerRequestToPartner(PartnerRequest request);

    @Mapping(target = "bonuses", source = "bonuses")
    PartnerResponse partnerToPartnerResponse(Partner partner);
}
