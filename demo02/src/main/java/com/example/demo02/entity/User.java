package com.example.demo02.entity;

import jakarta.persistence.*;
import com.example.demo02.dto.UserDTO;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String email;

    private String full_name;

    private String user_phone;

    private String user_dob;

    private String user_dp;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty_level;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Timestamp created_at;

    private Timestamp updated_at;

    @Column(name = "tot_points")
    private int totPoints;

    @Column(name = "user_rank")
    private int userRank;

    private String security_que_1;

    private String security_ans_1;

    // Many-to-many relationship for shared dragons among multiple users
    @ManyToMany
    @JoinTable(
            name = "user_dragon",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dragon_id")
    )
    private List<Dragon> dragons;

    public User() {
        this.difficulty_level = DifficultyLevel.Easy;  // Default to Easy
        this.role = Role.user;  // Default to User
    }

    public User(UserDTO userDTO) {
        this.userName = userDTO.getUserName();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.full_name = userDTO.getFull_name();
        this.user_phone = userDTO.getUser_phone();
        this.user_dob = userDTO.getUser_dob();
        this.user_dp = userDTO.getUser_dp();
        this.difficulty_level = userDTO.getDifficulty_level();
        this.role = userDTO.getRole();
        this.created_at = userDTO.getCreated_at();
        this.updated_at = userDTO.getUpdated_at();
        this.totPoints = userDTO.getTotPoints();
        this.userRank = userDTO.getUserRank();
        this.security_que_1 = userDTO.getSecurity_que_1();
        this.security_ans_1 = userDTO.getSecurity_ans_1();
    }
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
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
        return totPoints;
    }

    public void setTotPoints(int tot_points) {
        this.totPoints = tot_points;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    public String getSecurity_que_1() {
        return security_que_1;
    }

    public void setSecurity_que_1(String security_que_1) {
        this.security_que_1 = security_que_1;
    }

    public String getSecurity_ans_1() {
        return security_ans_1;
    }

    public List<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }

}

