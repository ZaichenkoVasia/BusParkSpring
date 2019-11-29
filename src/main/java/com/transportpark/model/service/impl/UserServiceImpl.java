package com.transportpark.model.service.impl;

import com.transportpark.model.domain.User;
import com.transportpark.model.domain.UserType;
import com.transportpark.model.entity.UserEntity;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.repositories.UserRepository;
import com.transportpark.model.repositories.UserTypeRepository;
import com.transportpark.model.service.UserService;
import com.transportpark.model.service.mapper.UserMapper;
import com.transportpark.model.service.mapper.UserTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserMapper userMapper;
    private final UserTypeMapper userTypeMapper;

    @Override
    public User registration(User user) {
        if (Objects.isNull(user)) {
            log.warn("User is null");
            throw new InvalidDataRuntimeException("User is null");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info("Don't find user by this email");
            throw new EntityNotFoundRuntimeException("Don't find user by this email");
        }
        UserType type = userTypeMapper.userTypeEntityToUserType(userTypeRepository.findByType("driver")
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find user type by this type")));

        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserType(type);
        UserEntity userEntity = userMapper.userToUserEntity(user);
        UserEntity saveEntity = userRepository.save(userEntity);

        return userMapper.userEntityToUser(saveEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userMapper.userEntityToUser(userRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find user by this email")));
    }
}
