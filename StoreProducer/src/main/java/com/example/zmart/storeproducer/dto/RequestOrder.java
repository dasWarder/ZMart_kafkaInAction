package com.example.zmart.storeproducer.dto;

import com.example.zmart.storeproducer.model.Department;
import com.example.zmart.storeproducer.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {

    @Valid
    @NotNull
    private Collection<Product> products;

    @NotNull
    @JsonProperty("zip_code")
    private Integer zip;

    @NotNull
    @JsonProperty("card_number")
    @Pattern(regexp = "[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}")
    private String cardNumber;

    @NotNull
    private Department department;
}
