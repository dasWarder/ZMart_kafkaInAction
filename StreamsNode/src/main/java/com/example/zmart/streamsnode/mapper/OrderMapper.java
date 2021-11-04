package com.example.zmart.streamsnode.mapper;

import com.example.zmart.streamsnode.dto.BenefitsInfo;
import com.example.zmart.streamsnode.dto.PatternsInfo;
import com.example.zmart.streamsnode.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "clientId", source = "id")
    BenefitsInfo orderToBenefitsInfo(Order order);

    PatternsInfo orderToPatternsInfo(Order order);
}
