package ua.mycompany.buspark.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.BusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.mycompany.buspark.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {
    Optional<BusEntity> findByUser(UserEntity user);

    List<BusEntity> findByStatus(Status status);

    Page<BusEntity> findAll(Pageable pageable);
}
