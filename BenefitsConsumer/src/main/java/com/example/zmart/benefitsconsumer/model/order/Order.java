package com.example.zmart.benefitsconsumer.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;

    @JsonProperty("cost_of_purchase")
    private Double costOfPurchase;

    private Collection<Product> products;

    @JsonProperty("zip_code")
    private Integer zip;

    @JsonProperty("purchase_date")
    private LocalDateTime purchaseDate;

    @JsonProperty("card_number")
    private String cardNumber;

    private Department department;
}
