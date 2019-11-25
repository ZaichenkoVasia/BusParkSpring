package com.spring.model.repositories;

import com.spring.model.entity.AssignmentEntity;
import com.spring.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {

    List<AssignmentEntity> findAllByRoute(RouteEntity route);
}
