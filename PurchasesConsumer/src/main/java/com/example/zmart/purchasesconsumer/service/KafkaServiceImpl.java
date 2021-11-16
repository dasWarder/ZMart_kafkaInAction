package com.example.zmart.purchasesconsumer.service;


import com.example.zmart.purchasesconsumer.dao.OrderRepository;
import com.example.zmart.purchasesconsumer.dto.OrderRequest;
import com.example.zmart.purchasesconsumer.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    @Override
    @KafkaListener(topics = "purchases", groupId = "purchases_group_id")
    public void consume(String serializedProduct) throws JsonProcessingException {

        log.info("Consume a serialized object as a string");

        OrderRequest orderRequest = objectMapper.readValue(serializedProduct, OrderRequest.class);
        Order order = Order.builder()
                .clientId(orderRequest.getId())
                .cardNumber(orderRequest.getCardNumber())
                .department(orderRequest.getDepartment().getName())
                .products(orderRequest.getProducts())
                .costOfPurchase(orderRequest.getCostOfPurchase())
                .purchaseDate(orderRequest.getPurchaseDate())
                .zip(orderRequest.getZip())
                .build();

        Order storedOrder = orderService.saveOrder(order);
        log.info("ElasticSearch stored: {}", storedOrder);
    }
}
