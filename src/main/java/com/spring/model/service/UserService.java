package com.spring.model.service;

import com.spring.model.domain.User;

public interface UserService {

    User findByEmailAndPassword(String email, String password);

    User registration(User user);
}
