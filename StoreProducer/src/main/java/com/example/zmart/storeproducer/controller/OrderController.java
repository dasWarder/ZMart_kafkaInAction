package com.example.zmart.storeproducer.controller;

import com.example.zmart.storeproducer.dto.RequestOrder;
import com.example.zmart.storeproducer.dto.ResponseOrder;
import com.example.zmart.storeproducer.mapper.OrderMapper;
import com.example.zmart.storeproducer.model.Order;
import com.example.zmart.storeproducer.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper mapper;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody @NotNull @Valid RequestOrder requestOrder) {

        log.info("REQUEST: {}", requestOrder.toString());
        Order order = mapper.requestOrderToOrder(requestOrder);
        log.info("ORDER: {}", order.toString());
        Order sentOrder = orderService.produceOrder(order);
        ResponseOrder responseDto = mapper.orderToResponseOrder(sentOrder);

        return ResponseEntity.ok(responseDto);
    }
}
