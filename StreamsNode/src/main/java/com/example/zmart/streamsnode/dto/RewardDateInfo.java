package com.example.zmart.streamsnode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardDateInfo {

    private Integer totalReward;

    private LocalDateTime lastPurchaseDate;
}
