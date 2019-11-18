package ua.mycompany.buspark.model.service;

import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.domain.Route;
import ua.mycompany.buspark.model.domain.enums.Status;

import java.util.List;

public interface RouteService {
    List<Route> findAll(Integer currentPage, Integer recordsPerPage);

    long showNumberOfRows();

    Route findByBus(Bus bus);

    List<Route> findByStatus(Status status);

    List<Route> findByArrival(String arrival);

    List<Route> findByDeparture(String departure);
}
