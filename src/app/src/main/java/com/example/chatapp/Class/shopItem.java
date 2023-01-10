package com.example.chatapp.Class;

import android.graphics.Bitmap;

import java.io.Serializable;

public class shopItem implements Serializable {
    private String name;
    private Integer price;
    private Bitmap image;
    private String description;
    private Boolean isPhysical;

    public shopItem(String name, Integer price, String description, Boolean isPhysical) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isPhysical = isPhysical;
    }

    public shopItem(String name, Integer price, Bitmap image, String description, Boolean isPhysical) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.isPhysical = isPhysical;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getPhysical() {
        return isPhysical;
    }
}
