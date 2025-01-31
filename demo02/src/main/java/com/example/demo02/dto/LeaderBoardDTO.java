package com.example.demo02.dto;

public class LeaderBoardDTO {

    private String userName;
    private int totPoints;
    private int userRank;

    // Constructor
    public LeaderBoardDTO(String userName, int totPoints, int userRank) {
        this.userName = userName;
        this.totPoints = totPoints;
        this.userRank = userRank;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotPoints() {
        return totPoints;
    }

    public void setTotPoints(int totPoints) {
        this.totPoints = totPoints;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }
}
