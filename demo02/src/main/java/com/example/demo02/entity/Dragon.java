package com.example.demo02.entity;

import com.example.demo02.dto.DragonDTO;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dragons")
public class Dragon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dragon_id;

    private String dragon_name;
    private int age;
    private String dragon_image;
    private double price;
    private String rarity;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "user_dragon",
            joinColumns = @JoinColumn(name = "dragon_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;


    public Dragon() {}


    public Dragon(DragonDTO dragonDTO) {
        this.dragon_name = dragonDTO.getDragon_name();
        this.age = dragonDTO.getAge();
        this.dragon_image = dragonDTO.getDragon_image();
        this.price = dragonDTO.getPrice();
        this.rarity = dragonDTO.getRarity();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
