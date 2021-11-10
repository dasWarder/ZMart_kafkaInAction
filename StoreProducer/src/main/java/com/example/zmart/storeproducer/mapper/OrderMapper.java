package com.example.zmart.storeproducer.mapper;


import com.example.zmart.storeproducer.dto.RequestOrder;
import com.example.zmart.storeproducer.dto.ResponseOrder;
import com.example.zmart.storeproducer.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface OrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "costOfPurchase", ignore = true)
    Order requestOrderToOrder(RequestOrder requestOrder);

    ResponseOrder orderToResponseOrder(Order order);

    @AfterMapping
    default void setPurchaseDate(@MappingTarget Order.OrderBuilder target) {
        target.purchaseDate(LocalDateTime.now());
    }
}
