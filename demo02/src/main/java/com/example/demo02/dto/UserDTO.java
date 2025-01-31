package com.example.demo02.dto;

import com.example.demo02.entity.DifficultyLevel;
import com.example.demo02.entity.Role;
import com.example.demo02.entity.User;


import java.sql.Timestamp;

public class UserDTO {

    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String full_name;
    private String user_phone;
    private String user_dp;
    private DifficultyLevel difficulty_level;
    private Role role;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int totPoints;
    private String user_dob;// Changed to String
    private int userRank;
    private String security_que_1;
    private String security_ans_1;

    public UserDTO() {
        this.difficulty_level = DifficultyLevel.Easy;  // Default to Easy
        this.role = Role.user;  // Default to User
    }

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.full_name = user.getFull_name();
        this.user_phone = user.getUser_phone();
        this.user_dp = user.getUser_dp();
        this.difficulty_level = user.getDifficulty_level();
        this.role = user.getRole();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
        this.totPoints = user.getTotPoints();
        this.user_dob = user.getUser_dob();
        this.userRank = user.getUserRank();
        this.security_que_1 = user.getSecurity_que_1();
        this.security_ans_1 = user.getSecurity_ans_1();


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

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    // Getter and Setter for tot_points
    public int getTotPoints() {
        return totPoints;
    }

    public void setTotPoints(int totPoints) {
        this.totPoints = totPoints;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) { // Set as String
        this.user_dob = user_dob;
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

    public void setSecurity_ans_1(String security_ans_1) {
        this.security_ans_1 = security_ans_1;
    }

}
