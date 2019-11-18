package ua.mycompany.buspark.model.repository;

import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findByStatus(Status status);

    Page<UserEntity> findAll(Pageable pageable);
}
