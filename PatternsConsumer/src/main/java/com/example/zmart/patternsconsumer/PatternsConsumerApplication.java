package com.example.zmart.patternsconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class PatternsConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatternsConsumerApplication.class, args);
  }
}
