package ua.mycompany.buspark.model.service;

import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.domain.User;
import ua.mycompany.buspark.model.domain.enums.Status;

import java.util.List;

public interface BusService {
    List<Bus> findAll(Integer currentPage, Integer recordsPerPage);

    long showNumberOfRows();

    Bus findByUser(User user);

    List<Bus> findByStatus(Status status);
}
