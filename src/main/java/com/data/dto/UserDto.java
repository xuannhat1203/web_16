package com.data.dto;

import com.data.service.RoleAuth;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    private int id;
    @NotBlank(message = "Username khong duoc de trong")
    private String username;
    @NotNull(message = "Mat khau khong duoc de trong")
    @Min(value = 6, message = "Mat khau phai co it nhat 6 ky tu")
    private String password;
    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong hop le")
    private String email;
    private RoleAuth role;
    private boolean status;
    public UserDto() {
    }
    public UserDto(int id, String username, String password, String email, RoleAuth role, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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
}
