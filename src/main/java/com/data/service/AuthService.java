package com.data.service;

import com.data.model.User;

import java.util.List;

public interface AuthService {
    List<User> getAllUsers();
    boolean addUser(User user);
}
