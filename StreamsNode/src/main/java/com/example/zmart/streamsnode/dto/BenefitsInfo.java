package com.example.zmart.streamsnode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"client_id", "cost_of_purchase" })
public class BenefitsInfo {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("cost_of_purchase")
    private Double costOfPurchase;
}
