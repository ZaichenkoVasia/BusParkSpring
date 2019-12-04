package com.transportpark.model.service.impl;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.RouteEntity;
import com.transportpark.model.exception.AssignmentsNotExistRuntimeException;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.exception.RouteNotExistRuntimeException;
import com.transportpark.model.repositories.AssignmentRepository;
import com.transportpark.model.repositories.RouteRepository;
import com.transportpark.model.service.BusService;
import com.transportpark.model.service.RouteService;
import com.transportpark.model.service.mapper.AssignmentMapper;
import com.transportpark.model.service.mapper.RouteMapper;
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
    public Assignment addAssignment(Assignment assignment) {
        if (Objects.isNull(assignment)) {
            log.warn("Incorrect assignment data");
            throw new InvalidDataRuntimeException("Incorrect assignment data");
        }
        Bus existsBus = busService.findByCode(assignment.getBus().getCode());
        assignment.setBus(existsBus);
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
        if (Objects.isNull(assignments) || assignments.isEmpty() || Objects.isNull(count) || count < 0) {
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
