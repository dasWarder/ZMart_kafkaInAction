package com.example.zmart.benefitsconsumer.dao;

import com.example.zmart.benefitsconsumer.model.bonus.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BonusRepository extends JpaRepository<Bonus, Long> {

  List<Bonus> getBonusesByPartnerId(Long id);

  List<Bonus> getBonusesByRewardPointsBeforeAndPartnerId(Long rewardPoints, Long id);

  Optional<Bonus> getBonusByIdAndPartnerId(Long bonusId, Long partnerId);

  void deleteBonusByIdAndPartnerId(Long bonusId, Long partnerId);
}
