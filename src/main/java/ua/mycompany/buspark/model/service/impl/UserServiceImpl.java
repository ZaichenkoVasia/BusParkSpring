package ua.mycompany.buspark.model.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mycompany.buspark.model.domain.User;
import ua.mycompany.buspark.model.entity.UserEntity;
import ua.mycompany.buspark.model.repository.UserRepository;
import ua.mycompany.buspark.model.service.UserService;
import ua.mycompany.buspark.model.service.exception.EntityNotFoundRuntimeException;
import ua.mycompany.buspark.model.service.exception.InvalidPaginatingException;
import ua.mycompany.buspark.model.service.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, UserMapper mapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("User is registered");
            throw new EntityNotFoundRuntimeException("User is registered");
        }
        String encoded = encoder.encode(user.getPassword());
        User userWithEncodedPass = new User(user, encoded);
        UserEntity entity = userRepository.save(mapper.userToUserEntity(userWithEncodedPass));

        return mapper.userEntityToUser(entity);
    }

    @Override
    public User login(String email, String password) {
        String encodedPassword = encoder.encode(password);
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        if (!entity.isPresent()) {
            log.warn("Can not find user");
            throw new EntityNotFoundException("Can not find user");
        } else {
            if (entity.get().getPassword().equals(encodedPassword)) {
                return mapper.userEntityToUser(entity.get());
            } else {
                log.warn("Incorrect password");
                throw new EntityNotFoundException("Incorrect password");
            }
        }
    }

    @Override
    public List<User> findAll(Integer currentPage, Integer recordsPerPage) {
        if (currentPage <= 0 || recordsPerPage <= 0) {
            log.error("Invalid pagination counters");
            throw new InvalidPaginatingException("Invalid pagination counters");
        }
        PageRequest pageRequest = PageRequest.of(currentPage, recordsPerPage);
        Page<UserEntity> result = userRepository.findAll(pageRequest);

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(mapper::userEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public long showNumberOfRows() {
        return userRepository.count();
    }
}
