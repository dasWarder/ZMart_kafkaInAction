package com.example.zmart.streamsnode.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamsUtil {

    public static String cloakCardNumber(String cardNumber) {

        log.info("Cloaking a card number");

        String[] cardParts = cardNumber.split("-");

        for(int i = 0; i < cardParts.length - 1; i++) {
            String cardPart = cardParts[i];
            cardParts[i] = cardPart.replaceAll("\\d", "X");
        }

        String cloakedCardNumber = String.join("-", cardParts);

        return cloakedCardNumber;
    }

}
