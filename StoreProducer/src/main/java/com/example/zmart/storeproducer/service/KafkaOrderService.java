package com.example.zmart.storeproducer.service;

import com.example.zmart.storeproducer.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaOrderService implements OrderService {

    private final ObjectMapper objectMapper;

    private final OrderProcedureService procedureService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public Order produceOrder(Order order) {

        log.info("Produce an order to the STORE topic");

        Order updatedOrder = updateOrderData(order);
        String readySerializeOrder = orderToString(updatedOrder);

        kafkaTemplate.send("store", "order", readySerializeOrder);

        return updatedOrder;
    }

    private Order updateOrderData(Order order) {

        Double totalCost = procedureService.calculateTotalPurchaseCost(order.getProducts());
        order.setCostOfPurchase(totalCost);

        return order;
    }

    private String orderToString(Order order) {

        try {

            log.info("Write an order with ID = {} as a string", order.getId());

            return objectMapper.writeValueAsString(order);

        } catch (JsonProcessingException e) {

            log.error("Exception occurred during writing an order with ID = {} as a string", order.getId());

            throw new RuntimeException(e.getMessage());
        }
    }
}
