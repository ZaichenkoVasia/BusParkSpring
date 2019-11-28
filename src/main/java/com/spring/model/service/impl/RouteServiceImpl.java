package com.spring.model.service.impl;

import com.spring.model.domain.Assignment;
import com.spring.model.domain.Bus;
import com.spring.model.domain.Route;
import com.spring.model.entity.AssignmentEntity;
import com.spring.model.entity.RouteEntity;
import com.spring.model.exception.AssignmentsNotExistRuntimeException;
import com.spring.model.exception.EntityNotFoundRuntimeException;
import com.spring.model.exception.InvalidDataRuntimeException;
import com.spring.model.exception.RouteNotExistRuntimeException;
import com.spring.model.repositories.AssignmentRepository;
import com.spring.model.repositories.RouteRepository;
import com.spring.model.service.BusService;
import com.spring.model.service.RouteService;
import com.spring.model.service.mapper.AssignmentMapper;
import com.spring.model.service.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteServiceImpl implements RouteService {

    private static final int CANCELED = 1;
    private static final int NOT_CANCELED = 0;
    private final BusService busService;
    private final RouteRepository routeRepository;
    private final AssignmentRepository assignmentRepository;
    private final RouteMapper routeMapper;
    private final AssignmentMapper assignmentMapper;

    @Override
    public Assignment addAssignment(Integer code, Double tax, Integer journey) {
        if (Objects.isNull(code) || Objects.isNull(tax) || Objects.isNull(journey)
                || tax < 0 || journey < 0) {
            log.warn("Incorrect assignment data");
            throw new InvalidDataRuntimeException("Incorrect assignment data");
        }
        Bus existsBus = busService.findByCode(code);
        Assignment assignment = new Assignment();
        assignment.setBus(existsBus);
        assignment.setTax(tax);
        assignment.setJourney(journey);
        assignment.setCanceled(NOT_CANCELED);

        return assignment;
    }

    @Override
    public void addRoute(List<Assignment> assignments) {
        Route route = new Route();
        route.setStatus(NOT_CANCELED);
        route = routeMapper.routeEntityToRoute(routeRepository.save(routeMapper.routeToRouteEntity(route)));

        for (Assignment assignment : assignments) {
            assignment.setRoute(route);
            AssignmentEntity assignmentEntity = assignmentMapper.assignmentToAssignmentEntity(assignment);
            assignmentRepository.save(assignmentEntity);
        }
    }

    @Override
    public Route findById(Long routeId) {
        return routeMapper.routeEntityToRoute(routeRepository.findById(routeId)
                .orElse(null));
    }

    @Override
    public List<Assignment> findAssignmentsByRoute(Long routeId) {
        Route route = routeMapper.routeEntityToRoute(routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find route by this id")));

        RouteEntity routeEntity = routeMapper.routeToRouteEntity(route);
        List<AssignmentEntity> assignmentsEntities = assignmentRepository.findAllByRoute(routeEntity);

        return assignmentsEntities.isEmpty() ?
                Collections.emptyList() :
                assignmentsEntities.stream()
                        .map(assignmentMapper::assignmentEntityToAssignment)
                        .collect(Collectors.toList());
    }

    @Override
    public void cancelRouteAssignments(List<Assignment> assignments, Integer count) {
        if (assignments.isEmpty() || Objects.isNull(count) || count < 0) {
            log.warn("Assignments not exist");
            throw new AssignmentsNotExistRuntimeException("Assignments not exist");
        }
        Assignment assignment = assignments.get(count - 1);
        assignment.setCanceled(CANCELED);
        AssignmentEntity assignmentEntity = assignmentMapper.assignmentToAssignmentEntity(assignment);
        assignmentRepository.save(assignmentEntity);
        Route route = assignment.getRoute();
        RouteEntity routeEntity = routeMapper.routeToRouteEntity(route);
        routeRepository.save(routeEntity);
    }

    @Override
    public void cancelRouteAssignments(Route route) {
        if (Objects.isNull(route)) {
            log.warn("Route not exist");
            throw new RouteNotExistRuntimeException("Route not exist");
        }
        route.setStatus(CANCELED);
        RouteEntity routeEntity = routeMapper.routeToRouteEntity(route);
        routeRepository.save(routeEntity);
    }
}
