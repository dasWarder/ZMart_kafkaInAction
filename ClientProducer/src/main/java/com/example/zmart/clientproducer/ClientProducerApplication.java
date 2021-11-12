package com.example.zmart.clientproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ClientProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientProducerApplication.class, args);
  }
}
