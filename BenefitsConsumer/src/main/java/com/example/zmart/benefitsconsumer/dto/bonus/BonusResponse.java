package com.example.zmart.benefitsconsumer.dto.bonus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "partner_id", "reward_points", "description"})
public class BonusResponse {

  @Min(1)
  @NotNull
  private Long id;

  @Min(1)
  @NotNull
  @JsonProperty(value = "reward_points")
  private Long rewardPoints;

  @NotNull
  private String description;

  @Min(1)
  @NotNull
  @JsonProperty(value = "partner_id")
  private Long partnerId;
}
