package com.example.zmart.storeproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 512)
    private String description;

    @NotNull
    private ProductType type;

    @NotNull
    private Double cost;
}
