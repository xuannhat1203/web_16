package com.data.service;

import com.data.model.User;
import com.data.repository.AuthRepository;
import com.data.repository.AuthRepositoryImp;

import java.util.List;

public class AuthServiceImp implements AuthService {
    public AuthRepository authRepository;
    public AuthServiceImp() {
        authRepository = new AuthRepositoryImp();
    }
    @Override
    public List<User> getAllUsers() {
        return authRepository.getAllUsers();
    }
    @Override
    public boolean addUser(User user) {
        return authRepository.addUser(user);
    }
}
