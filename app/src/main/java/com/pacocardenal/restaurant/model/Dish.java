package com.pacocardenal.restaurant.model;

import java.io.Serializable;

public class Dish implements Serializable{

    private String mName;
    private String mDescription;
    private String mAllergens;
    private Float mPrice;
    private int mImage;

    public Dish(String name, String description, String allergens, Float price, int image) {
        mName = name;
        mDescription = description;
        mAllergens = allergens;
        mPrice = price;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAllergens() {
        return mAllergens;
    }

    public void setAllergens(String allergens) {
        mAllergens = allergens;
    }

    public String getPrice() {
        //return (String.valueOf(mPrice) + "€");
        return String.format("%.02f", mPrice) + "€";
    }

    public Float price() { return mPrice; }

    public void setPrice(Float price) {
        mPrice = price;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    @Override
    public String toString() {
        return mName;
    }
}
