package com.example.zmart.benefitsconsumer.dto.partner;

import com.example.zmart.benefitsconsumer.dto.bonus.BonusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponse {

    @Min(1)
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Set<BonusResponse> bonuses;
}
