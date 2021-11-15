package com.example.zmart.benefitsconsumer.service.kafka;

import com.example.zmart.benefitsconsumer.dto.benefits.BenefitsInfo;
import com.example.zmart.benefitsconsumer.dto.info.PartnerBonusesRequest;
import com.example.zmart.benefitsconsumer.dto.info.PartnerBonusesResponse;
import com.example.zmart.benefitsconsumer.dto.info.PartnersBonuses;
import com.example.zmart.benefitsconsumer.model.Bonus;
import com.example.zmart.benefitsconsumer.model.Partner;
import com.example.zmart.benefitsconsumer.service.bonus.BonusService;
import com.example.zmart.benefitsconsumer.service.email.EmailNotificationService;
import com.example.zmart.benefitsconsumer.service.partner.PartnerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumeService {

  private final ObjectMapper objectMapper;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final BonusService bonusService;

  private final PartnerService partnerService;

  private final EmailNotificationService emailNotificationService;

  @Override
  @KafkaListener(topics = "benefits", groupId = "benefits_group_1")
  public void consume(String serializedObject) throws JsonProcessingException {

    log.info("Received benefits info: {}", serializedObject);

    BenefitsInfo benefitsInfo = objectMapper.readValue(serializedObject, BenefitsInfo.class);
    String clientId = benefitsInfo.getClientId();

    final Long totalRewardPoints = (long) benefitsInfo.getTotalRewardPoints();
    List<Partner> partners = partnerService.getPartners();

    List<Bonus> bonusesList = receiveListOfBonuses(partners, totalRewardPoints);
    List<PartnersBonuses> partnerBonuses = receivePartnersBonusesList(bonusesList);
    PartnerBonusesRequest partnerBonusesRequest =
        createPartnerBonusesRequestObject(clientId, partnerBonuses);

    String serializedResponse = objectMapper.writeValueAsString(partnerBonusesRequest);
    kafkaTemplate.send("clients_data", serializedResponse);
  }

  @KafkaListener(topics = "users", groupId = "benefits_group_1")
  public void consumeClient(String serializedResponse) throws JsonProcessingException {

    log.info("Received an object = {}", serializedResponse);
    PartnerBonusesResponse partnerBonusesResponse =
        objectMapper.readValue(serializedResponse, PartnerBonusesResponse.class);

    emailNotificationService.sendBenefits(partnerBonusesResponse);
  }

  private List<Bonus> receiveListOfBonuses(List<Partner> partners, Long totalRewardPoints) {
    List<Bonus> bonusesList =
        partners.stream()
            .map(
                p -> {
                  Optional<Bonus> first =
                      bonusService
                          .getBonusesByRewardPointAndPartnerId(totalRewardPoints, p.getId())
                          .stream()
                          .sorted(Comparator.comparing(Bonus::getRewardPoints).reversed())
                          .findFirst();

                  if (first.isPresent()) {
                    return first.get();
                  }
                  return null;
                })
            .filter(b -> Objects.nonNull(b))
            .collect(Collectors.toList());

    return bonusesList;
  }

  private List<PartnersBonuses> receivePartnersBonusesList(List<Bonus> bonusesList) {

    List<PartnersBonuses> partnersBonuses = new ArrayList<>();

    bonusesList.forEach(
        b -> {
          PartnersBonuses partnerBonus =
              PartnersBonuses.builder()
                  .partnerName(b.getPartner().getName())
                  .bonusDescription(b.getDescription())
                  .build();

          partnersBonuses.add(partnerBonus);
        });

    return partnersBonuses;
  }

  private PartnerBonusesRequest createPartnerBonusesRequestObject(
      String clientId, List<PartnersBonuses> partnerBonuses) {
    PartnerBonusesRequest partnerBonusesRequest =
        PartnerBonusesRequest.builder().clientId(clientId).bonuses(partnerBonuses).build();

    return partnerBonusesRequest;
  }
}
