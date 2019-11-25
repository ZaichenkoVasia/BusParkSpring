package com.spring.model.repositories;

import com.spring.model.entity.UserEntity;
import com.spring.model.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findUserByUserType(UserTypeEntity userType);

    Optional<UserEntity> findByLogin(String login);
}
