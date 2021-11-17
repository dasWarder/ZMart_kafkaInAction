package com.example.zmart.storeproducer.model;

import lombok.Data;


public enum ProductType {

    BABY("baby"),
    ARTS("arts"),
    BEAUTY("beauty"),
    BOOKS("books"),
    COMPUTERS("computers"),
    ELECTRONICS("electronics"),
    FASHION("fashion"),
    HOME("home"),
    VIDEO_GAMES("video games"),
    FOOD("food"),
    BEVERAGE("beverage");

    private String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
