package com.example.demo02.dto;

import com.example.demo02.entity.Dragon;

public class DragonDTO {

    private Long dragon_id;
    private String dragon_name;
    private int age;
    private String dragon_image;
    private double price;
    private String rarity;


    public DragonDTO() {}


    public DragonDTO(Dragon dragon) {
        this.dragon_id = dragon.getDragon_id();
        this.dragon_name = dragon.getDragon_name();
        this.age = dragon.getAge();
        this.dragon_image = dragon.getDragon_image();
        this.price = dragon.getPrice();
        this.rarity = dragon.getRarity();
    }


    public Long getDragon_id() {
        return dragon_id;
    }

    public void setDragon_id(Long dragon_id) {
        this.dragon_id = dragon_id;
    }

    public String getDragon_name() {
        return dragon_name;
    }

    public void setDragon_name(String dragon_name) {
        this.dragon_name = dragon_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDragon_image() {
        return dragon_image;
    }

    public void setDragon_image(String dragon_image) {
        this.dragon_image = dragon_image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
