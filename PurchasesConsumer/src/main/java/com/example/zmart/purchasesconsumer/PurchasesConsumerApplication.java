package com.example.zmart.purchasesconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class PurchasesConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PurchasesConsumerApplication.class, args);
  }
}
