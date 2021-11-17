package com.example.zmart.benefitsconsumer.model.bonus;

import com.example.zmart.benefitsconsumer.model.partner.Partner;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bonus")
public class Bonus {

    @Id
    @SequenceGenerator(
            name = "partner_seq",
            sequenceName = "partner_seq",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
    private Long id;

    @Column(name = "reward_points")
    private Long rewardPoints;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    private Partner partner;
}
