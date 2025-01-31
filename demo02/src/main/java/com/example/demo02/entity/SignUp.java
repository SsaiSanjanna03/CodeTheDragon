package com.example.demo02.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


// can remove this entity as all methods are defined in user 
@Entity
@Table(name = "users")
public class SignUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    @JsonProperty("user_name")
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String full_name;
    private String user_phone;
    private Date user_dob;
    private String user_dp;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty_level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(updatable = false)
    private Timestamp created_at;

    private Timestamp updated_at;

    @Column(name = "tot_points")
    private int tot_points;

    // Many-to-many relationship for shared dragons among multiple users
    @ManyToMany
    @JoinTable(
            name = "user_dragon",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dragon_id")
    )
    private List<Dragon> dragons;

    // Method to automatically set timestamps
    @PrePersist
    protected void onCreate() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public Date getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(Date user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_dp() {
        return user_dp;
    }

    public void setUser_dp(String user_dp) {
        this.user_dp = user_dp;
    }

    public DifficultyLevel getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(DifficultyLevel difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public int getTotPoints() {
        return tot_points;
    }

    public void setTotPoints(int tot_points) {
        this.tot_points = tot_points;
    }

    public List<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "user_id=" + userId +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


