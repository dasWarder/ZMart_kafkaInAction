package com.example.zmart.storeproducer.service;

import com.example.zmart.storeproducer.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
public class OrderProcedureServiceImpl implements OrderProcedureService {

  @Override
  public String generateRandomId() {

    log.info("Generate random UUID");

    return UUID.randomUUID().toString();
  }

  @Override
  public Double calculateTotalPurchaseCost(Collection<Product> products) {

    log.info("Calculate sum of all products");

    Double sum = products.stream().map(p -> p.getCost()).reduce(0.0d, (s, c) -> s + c);

    return sum;
  }
}
