package com.example.zmart.benefitsconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partner")
public class Partner {

  @Id
  @SequenceGenerator(
      name = "partner_seq",
      sequenceName = "partner_seq",
      initialValue = 1,
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
  private Long id;

  private String name;

  private String description;

  @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Bonus> bonuses;
}
