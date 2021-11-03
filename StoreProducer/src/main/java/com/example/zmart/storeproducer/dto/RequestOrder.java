package com.example.zmart.storeproducer.dto;

import com.example.zmart.storeproducer.model.Department;
import com.example.zmart.storeproducer.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {

    private Collection<Product> products;

    @JsonProperty("zip_code")
    private Integer zip;

    @JsonProperty("card_number")
    private String cardNumber;

    private Department department;
}
