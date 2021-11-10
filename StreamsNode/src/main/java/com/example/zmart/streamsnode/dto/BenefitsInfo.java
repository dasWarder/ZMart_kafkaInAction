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
@JsonPropertyOrder(
    value = {
      "client_id",
      "cost_of_purchase",
      "current_reward_points",
      "days_from_last_purchase",
      "total_reward_points"
    })
public class BenefitsInfo {

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("cost_of_purchase")
  private Double costOfPurchase;

  @JsonProperty("current_reward_points")
  private Integer currentRewardPoints;

  @JsonProperty("days_from_last_purchase")
  private Integer daysFromLastPurchase;

  @JsonProperty("total_reward_points")
  private Integer totalRewardPoints;
}
