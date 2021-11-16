package com.example.zmart.purchasesconsumer.service;


import com.example.zmart.purchasesconsumer.model.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);

    List<Order> findOrdersByClientId(String clientId);

    List<Order> findOrdersByDepartmentName(String department);

    List<Order> findOrdersByZipCode(Integer zip);
}
