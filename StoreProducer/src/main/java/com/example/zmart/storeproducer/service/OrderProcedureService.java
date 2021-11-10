package com.example.zmart.storeproducer.service;

import com.example.zmart.storeproducer.model.Product;
import java.util.Collection;

public interface OrderProcedureService {

    Double calculateTotalPurchaseCost(Collection<Product> products);
}
