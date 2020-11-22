package com.example.creatinglists;

public class ShopModel {
    public String name, address;
    public int rating, category;

    // alt + insert
    // select constructor
    // select all field
    // press ok


    public ShopModel() {
        // for firebase
    }

    public ShopModel(String name, String address, int rating, int category) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.category = category;
    }
}
