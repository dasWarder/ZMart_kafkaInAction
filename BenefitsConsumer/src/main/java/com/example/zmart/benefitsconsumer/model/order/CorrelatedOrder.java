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
public class CorrelatedOrder {

    @JsonProperty("client_id")
    private String id;

    @JsonProperty("first_purchase_date")
    private LocalDateTime firstPurchaseDate;

    @JsonProperty("second_purchase_date")
    private LocalDateTime secondPurchaseDate;

    @JsonProperty("purchased_item")
    private Collection<Product> purchasedItem;

    @JsonProperty("total_amount")
    private Double totalAmount;
}
