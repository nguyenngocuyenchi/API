package com.example.DataSample.service;

import java.util.Optional;

import com.example.DataSample.model.User;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserByUsername(String username);
}
