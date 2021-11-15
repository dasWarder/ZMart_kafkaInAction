package com.example.zmart.benefitsconsumer.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerBonusesRequest {

    private String clientId;

    private List<PartnersBonuses> bonuses;
}
