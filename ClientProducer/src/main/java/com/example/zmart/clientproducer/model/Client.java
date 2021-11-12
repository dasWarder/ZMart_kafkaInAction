package com.example.zmart.clientproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

  @Id
  @SequenceGenerator(
      name = "client_seq",
      sequenceName = "client_seq",
      initialValue = 1,
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
  private Long id;

  @JsonProperty("client_id")
  @Column(name = "client_id")
  private String clientId;

  @JsonProperty("first_name")
  @Column(name = "first_name")
  private String firstName;

  @JsonProperty("last_name")
  @Column(name = "last_name")
  private String lastName;

  private String email;
}
