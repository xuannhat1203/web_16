package com.data.model;

import com.data.service.RoleAuth;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private RoleAuth role;
    private boolean status;
    public User(){}
    public User(int id, String username, String password, String email, RoleAuth role, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public RoleAuth getRole() {
        return role;
    }

    public void setRole(RoleAuth role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
