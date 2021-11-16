package com.example.zmart.purchasesconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "purchase")
public class Order {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String clientId;

    @JsonProperty("cost_of_purchase")
    private Double costOfPurchase;
    
    @Field(type = FieldType.Nested, includeInParent = true)
    private Collection<Product> products;

    @JsonProperty("zip_code")
    @Field(type = FieldType.Keyword)
    private Integer zip;

    @JsonProperty("purchase_date")
    private LocalDateTime purchaseDate;

    @JsonProperty("card_number")
    private String cardNumber;

    private String department;
}
