package com.spring.model.service.impl;

import com.spring.model.domain.Assignment;
import com.spring.model.domain.Bus;
import com.spring.model.domain.Route;
import com.spring.model.entity.AssignmentEntity;
import com.spring.model.entity.RouteEntity;
import com.spring.model.exception.RouteNotExistRuntimeException;
import com.spring.model.exception.EntityNotFoundRuntimeException;
import com.spring.model.exception.AssignmentsNotExistRuntimeException;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteServiceImpl implements RouteService {

    private final BusService busService;
    private final RouteRepository routeRepository;
    private final AssignmentRepository assignmentRepository;
    private final RouteMapper routeMapper;
    private final AssignmentMapper assignmentMapper;

    @Override
    public Assignment addAssignment(Integer code, String name, Double tax, Integer journey) {
        Bus existsBus;
        if (code != null) {
            existsBus = busService.findByCode(code);
        } else {
            existsBus = busService.findByModel(name);
        }

        Assignment assignment = new Assignment();
        assignment.setBus(existsBus);
        assignment.setTax(tax);
        assignment.setJourney(journey != null ? journey : 0);
        assignment.setCanceled(0);

        return assignment;
    }

    @Override
    public void addRoute(List<Assignment> assignments) {
        Route route = new Route();
        route.setStatus(0);
        route = routeMapper.checkEntityToCheck(routeRepository.save(routeMapper.checkToCheckEntity(route)));

        for (Assignment assignment : assignments) {
            assignment.setRoute(route);
            assignmentRepository.save(assignmentMapper.assignmentToAssignmentEntity(assignment));
        }
    }

    @Override
    public Route findById(Long routeId) {
        return routeMapper.checkEntityToCheck(routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find route by this id")));
    }

    @Override
    public List<Assignment> findAssignmentsByRoute(Long routeId) {
        Route route = routeMapper.checkEntityToCheck(routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find route by this id")));

        RouteEntity routeEntity = routeMapper.checkToCheckEntity(route);
        List<AssignmentEntity> assignmentsEntities = assignmentRepository.findAllByRoute(routeEntity);

        return assignmentsEntities.isEmpty() ? Collections.emptyList()
                : assignmentsEntities.stream()
                .map(assignmentMapper::assignmentEntityToAssignment)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelRouteAssignments(List<Assignment> assignments, Integer count) {
        if (assignments != null && assignments.size() >= count && count > 0) {
            Assignment assignment = assignments.get(count - 1);
            assignment.setCanceled(1);
            assignmentRepository.save(assignmentMapper.assignmentToAssignmentEntity(assignment));
            Route route = assignment.getRoute();
            routeRepository.save(routeMapper.checkToCheckEntity(route));
        } else {
            log.info("Assignments not exist");
            throw new AssignmentsNotExistRuntimeException("Assignments not exist");
        }
    }

    @Override
    public void cancelRouteAssignments(Route route) {
        if (route != null) {
            route.setStatus(1);
            routeRepository.save(routeMapper.checkToCheckEntity(route));
        } else {
            log.info("Route not exist");
            throw new RouteNotExistRuntimeException("Route not exist");
        }
    }
}
