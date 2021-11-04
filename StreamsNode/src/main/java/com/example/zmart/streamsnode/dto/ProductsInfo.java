package com.example.zmart.streamsnode.dto;

import com.example.zmart.streamsnode.model.ProductType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = { "id", "name", "type", "cost" })
public class ProductsInfo {

    private Long id;

    private String name;

    private ProductType type;

    private Double cost;
}
