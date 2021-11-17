package com.example.zmart.benefitsconsumer.model.order;

public enum Department {

    STORE("store"),
    COFFEE("coffee"),
    ELECTRONIC("electronic");

    private String name;

    Department(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
