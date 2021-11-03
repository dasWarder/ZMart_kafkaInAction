package com.example.zmart.storeproducer.service;

import com.example.zmart.storeproducer.model.Product;
import java.util.Collection;

public interface OrderProcedureService {

    String generateRandomId();

    Double calculateTotalPurchaseCost(Collection<Product> products);
}
