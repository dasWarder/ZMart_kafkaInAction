package com.example.zmart.streamsnode.util;

import com.example.zmart.streamsnode.dto.BenefitsInfo;
import com.example.zmart.streamsnode.dto.RewardDateInfo;
import com.example.zmart.streamsnode.mapper.CustomStringObjectMapper;
import com.example.zmart.streamsnode.mapper.OrderMapper;
import com.example.zmart.streamsnode.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.kstream.ValueTransformerWithKeySupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CustomBenefitsTransformer
    implements ValueTransformerWithKeySupplier<String, String, String> {

  private final String storageName;

  private final OrderMapper orderMapper;

  private final CustomStringObjectMapper stringObjectMapper;

  @Override
  public ValueTransformerWithKey<String, String, String> get() {
    return new ValueTransformerWithKey<String, String, String>() {

      private KeyValueStore<String, String> store;

      @Override
      public void init(ProcessorContext processorContext) {
        store = (KeyValueStore<String, String>) processorContext.getStateStore(storageName);
      }

      @Override
      public String transform(String s1, String s2) {

        log.info("Transform order object to the benefitsInfo object with a key = {}", s1);

        Order orderObj = (Order) stringObjectMapper.stringToObject(s2, Order.class);

        BenefitsInfo benefitsInfo = mapOrderToBenefitsInfoWithCalculatedBonuses(orderObj);
        String serializeBenefitInfo = store.get(benefitsInfo.getClientId());

        if (Objects.nonNull(serializeBenefitInfo)) {
          updateBenefitsInfo(benefitsInfo, serializeBenefitInfo, orderObj);
        } else {
          benefitsInfo.setTotalRewardPoints(benefitsInfo.getCurrentRewardPoints());
          benefitsInfo.setDaysFromLastPurchase(0);
        }

        putUpdateAndPutNewRewardInfoIntoStorage(orderObj, benefitsInfo, store);
        String info = stringObjectMapper.objectToString(benefitsInfo);

        return info;
      }

      @Override
      public void close() {}
    };
  }

  private BenefitsInfo mapOrderToBenefitsInfoWithCalculatedBonuses(Order orderObj) {

    log.info(
        "Mapping order object with id = {} to the benefitsInfo object with a current purchase bonuses",
        orderObj.getId());

    Integer currentPurchaseBonuses = (int) Math.floor(orderObj.getCostOfPurchase());

    BenefitsInfo benefitsInfo = orderMapper.orderToBenefitsInfo(orderObj);
    benefitsInfo.setCurrentRewardPoints(currentPurchaseBonuses);

    return benefitsInfo;
  }

  private void updateBenefitsInfo(
      BenefitsInfo benefitsInfo, String serializeBenefitInfo, Order orderObj) {

    log.info("Update benefits info based on the storage data");

    RewardDateInfo lastDateRewardInfo =
        (RewardDateInfo)
            stringObjectMapper.stringToObject(serializeBenefitInfo, RewardDateInfo.class);

    benefitsInfo.setTotalRewardPoints(
        benefitsInfo.getCurrentRewardPoints() + lastDateRewardInfo.getTotalReward());
    benefitsInfo.setDaysFromLastPurchase(calculateDaysBetween(orderObj, lastDateRewardInfo));
  }

  private Integer calculateDaysBetween(Order cur, RewardDateInfo prev) {

    log.info("Calculate a rate based on the total cost the current purchase");

    return (int) ChronoUnit.DAYS.between(cur.getPurchaseDate(), prev.getLastPurchaseDate());
  }

  private void putUpdateAndPutNewRewardInfoIntoStorage(
          Order orderObj, BenefitsInfo benefitsInfo, KeyValueStore<String, String> store) {

    log.info("Update the storage for key = {}", orderObj.getId());

    RewardDateInfo curInfo = createNewRewardDateInfoObjectForStorage(orderObj, benefitsInfo);
    String storageUpdateDetails = stringObjectMapper.objectToString(curInfo);
    store.put(benefitsInfo.getClientId(), storageUpdateDetails);
  }

  private RewardDateInfo createNewRewardDateInfoObjectForStorage(
      Order orderObj, BenefitsInfo benefitsInfo) {

    log.info("Create a new rewardDateInfo object");

    RewardDateInfo curInfo =
        RewardDateInfo.builder()
            .lastPurchaseDate(orderObj.getPurchaseDate())
            .totalReward(benefitsInfo.getTotalRewardPoints())
            .build();

    return curInfo;
  }
}
