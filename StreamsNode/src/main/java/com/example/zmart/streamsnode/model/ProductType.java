package com.example.zmart.streamsnode.model;


public enum ProductType {

    BABY("baby"),
    ARTS("arts"),
    BEAUTY("beauty"),
    BOOKS("books"),
    COMPUTERS("computers"),
    ELECTRONICS("electronics"),
    FASHION("fashion"),
    HOME("home"),
    VIDEO_GAMES("video games");

    private String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}