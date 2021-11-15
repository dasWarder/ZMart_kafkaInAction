package com.example.zmart.benefitsconsumer.dto.info;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnersBonuses {

    private String partnerName;

    private String bonusDescription;
}
