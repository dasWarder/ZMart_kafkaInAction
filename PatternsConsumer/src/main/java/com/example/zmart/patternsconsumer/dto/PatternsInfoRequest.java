package com.example.zmart.patternsconsumer.dto;

import com.example.zmart.patternsconsumer.model.ProductsInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"zip_code", "products"})
public class PatternsInfoRequest {

  @JsonProperty("zip_code")
  @Field(type = FieldType.Keyword)
  private Integer zip;

  private Collection<ProductsInfo> products;
}
