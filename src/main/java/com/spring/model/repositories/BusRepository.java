package com.spring.model.repositories;

import com.spring.model.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {

    Optional<BusEntity> findByCode(int code);
}
