package com.transportpark.model.service;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Route;

import java.util.List;

public interface RouteService {

    Assignment addAssignment(Assignment assignment);

    void addRoute(List<Assignment> assignments);

    Route findById(Long routeId);

    List<Assignment> findAssignmentsByRoute(Long routeId);

    void cancelRouteAssignments(List<Assignment> assignments, Integer count);

    void cancelRouteAssignments(Route route);
}
