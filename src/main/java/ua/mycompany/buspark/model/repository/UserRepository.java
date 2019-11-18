package ua.mycompany.buspark.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);

    List<UserEntity> findByStatus(Status status);

    Page<UserEntity> findAll(Pageable pageable);
}
