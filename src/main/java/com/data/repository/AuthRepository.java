package com.data.repository;

import com.data.model.User;

import java.util.List;

public interface AuthRepository {
    List<User> getAllUsers();
    boolean addUser(User user);
}
