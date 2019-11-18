package ua.mycompany.buspark.model.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.domain.Route;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.BusEntity;
import ua.mycompany.buspark.model.entity.RouteEntity;
import ua.mycompany.buspark.model.repository.RouteRepository;
import ua.mycompany.buspark.model.service.RouteService;
import ua.mycompany.buspark.model.service.exception.EntityNotFoundRuntimeException;
import ua.mycompany.buspark.model.service.exception.InvalidPaginatingRuntimeException;
import ua.mycompany.buspark.model.service.mapper.BusMapper;
import ua.mycompany.buspark.model.service.mapper.RouteMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final BusMapper busMapper;
    private final RouteMapper routeMapper;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, BusMapper busMapper, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.busMapper = busMapper;
        this.routeMapper = routeMapper;
    }

    @Override
    public List<Route> findAll(Integer currentPage, Integer recordsPerPage) {
        if (currentPage <= 0 || recordsPerPage <= 0) {
            log.error("Invalid pagination counters");
            throw new InvalidPaginatingRuntimeException("Invalid pagination counters");
        }
        PageRequest pageRequest = PageRequest.of(currentPage, recordsPerPage);
        Page<RouteEntity> result = routeRepository.findAll(pageRequest);

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(routeMapper::routeEntityToRoute)
                .collect(Collectors.toList());
    }

    @Override
    public long showNumberOfRows() {
        return routeRepository.count();
    }

    @Override
    public Route findByBus(Bus bus) {
        if (bus == null) {
            log.warn("Bus is not exist");
            throw new EntityNotFoundException("Bus is not exist");
        }
        BusEntity busEntity = busMapper.busToBusEntity(bus);
        Optional<RouteEntity> routeEntity = routeRepository.findByBus(busEntity);

        RouteEntity routeEntityResult = routeEntity.
                orElseThrow(() -> {
                    log.warn("Route don't exist with this bus");
                    throw new EntityNotFoundRuntimeException("Route don't exist with this bus");
                });
        return routeMapper.routeEntityToRoute(routeEntityResult);
    }

    @Override
    public List<Route> findByStatus(Status status) {
        if (status == null) {
            log.warn("Status of route is null");
            throw new EntityNotFoundException("Status of route is null");
        }
        List<RouteEntity> result = routeRepository.findByStatus(status);
        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(routeMapper::routeEntityToRoute)
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> findByArrival(String arrival) {
        if (arrival == null) {
            log.warn("Arrival of bus is null");
            throw new EntityNotFoundException("Arrival of bus is null");
        }
        List<RouteEntity> result = routeRepository.findByArrival(arrival);
        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(routeMapper::routeEntityToRoute)
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> findByDeparture(String departure) {
        if (departure == null) {
            log.warn("Departure of bus is null");
            throw new EntityNotFoundException("Departure of bus is null");
        }
        List<RouteEntity> result = routeRepository.findByArrival(departure);
        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(routeMapper::routeEntityToRoute)
                .collect(Collectors.toList());
    }
}
