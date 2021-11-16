package com.example.zmart.purchasesconsumer.service;

import com.example.zmart.purchasesconsumer.dao.OrderRepository;
import com.example.zmart.purchasesconsumer.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderElasticSearchService implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public Order saveOrder(Order order) {

    log.info("Store a new order for a client with ID = {}", order.getClientId());

    return orderRepository.save(order);
  }

  @Override
  public List<Order> findOrdersByClientId(String clientId) {

    log.info("Fetch orders with a clientId = {}", clientId);

    return orderRepository.findOrdersByClientId(clientId);
  }

  @Override
  public List<Order> findOrdersByDepartmentName(String department) {

    log.info("Fetch orders with a department name = {}", department);

    return orderRepository.findOrdersByDepartment(department);
  }

  @Override
  public List<Order> findOrdersByZipCode(Integer zip) {

    log.info("Fetch orders with a zip code = {}", zip);

    return orderRepository.findOrdersByZip(zip);
  }
}
