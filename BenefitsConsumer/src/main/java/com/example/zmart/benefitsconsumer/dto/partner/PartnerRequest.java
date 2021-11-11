package com.example.zmart.benefitsconsumer.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
