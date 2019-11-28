package com.spring.model.service;

import com.spring.model.domain.User;
import com.spring.model.domain.UserType;

import java.util.List;

public interface UserService {
    User findByLoginAndPassword(String login, String password);

    User registration(User user);
}
