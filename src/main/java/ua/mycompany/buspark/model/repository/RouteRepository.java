package ua.mycompany.buspark.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.BusEntity;
import ua.mycompany.buspark.model.entity.RouteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    Optional<RouteEntity> findByBus(BusEntity bus);

    List<RouteEntity> findByStatus(Status status);

    List<RouteEntity> findByArrival(String arrival);

    List<RouteEntity> findByDeparture(String departure);

    Page<RouteEntity> findAll(Pageable pageable);
}
