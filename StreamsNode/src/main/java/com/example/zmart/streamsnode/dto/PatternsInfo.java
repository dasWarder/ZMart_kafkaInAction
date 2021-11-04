package com.example.zmart.streamsnode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = { "zip_code", "products" })
public class PatternsInfo {

    @JsonProperty("zip_code")
    private Integer zip;

    private Collection<ProductsInfo> products;
}
