package com.example.zmart.patternsconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "pattern")
@JsonPropertyOrder(value = {"id", "zip_code", "products"})
public class PatternsInfo {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @JsonProperty("zip_code")
  @Field(type = FieldType.Keyword)
  private Integer zip;

  private Collection<ProductsInfo> products;
}
