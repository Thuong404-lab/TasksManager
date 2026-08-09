package com.taskmanager.model;

public class User {
    private Integer id;
    private String userAccount;
    private String userName;
    private String userEmail;
    private String role;

    public User() {
    }

    public User(Integer id, String userAccount, String userName, String userEmail, String role) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUserAccount() { return userAccount; }
    public void setUserAccount(String userAccount) { this.userAccount = userAccount; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
