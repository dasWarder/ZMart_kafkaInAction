package com.example.zmart.benefitsconsumer.mappingService.bonus;

import com.example.zmart.benefitsconsumer.dto.bonus.BonusRequest;
import com.example.zmart.benefitsconsumer.dto.bonus.BonusResponse;
import com.example.zmart.benefitsconsumer.mappingService.partner.PartnerMapper;
import com.example.zmart.benefitsconsumer.model.bonus.Bonus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PartnerMapper.class)
public interface BonusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partner", ignore = true)
    Bonus bonusRequestToBonus(BonusRequest bonusRequest);

    @Mapping(target = "partnerId", source = "partner.id")
    BonusResponse bonusToBonusResponse(Bonus bonus);
}
