package com.example.zmart.purchasesconsumer.dao;

import com.example.zmart.purchasesconsumer.model.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderRepository extends ElasticsearchRepository<Order, String> {

    List<Order> findOrdersByZip(Integer zip);

    List<Order> findOrdersByClientId(String clientId);

    List<Order> findOrdersByDepartment(String department);
}
