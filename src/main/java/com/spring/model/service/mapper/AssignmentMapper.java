package com.spring.model.service.mapper;

import com.spring.model.domain.Assignment;
import com.spring.model.domain.Bus;
import com.spring.model.domain.Route;
import com.spring.model.entity.AssignmentEntity;
import com.spring.model.entity.BusEntity;
import com.spring.model.entity.RouteEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignmentMapper {
    private RouteMapper routeMapper;
    private BusMapper busMapper;

    public Assignment assignmentEntityToAssignment(AssignmentEntity assignmentEntity) {
        if (assignmentEntity == null) {
            return null;
        }

        Route route = routeMapper.checkEntityToCheck(assignmentEntity.getRoute());
        Bus bus = busMapper.busEntityToBus(assignmentEntity.getBus());

        return Assignment.builder()
                .id(assignmentEntity.getId())
                .tax(assignmentEntity.getTax())
                .journey(assignmentEntity.getJourney())
                .canceled(assignmentEntity.getCanceled())
                .route(route)
                .bus(bus)
                .build();
    }

    public AssignmentEntity assignmentToAssignmentEntity(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        RouteEntity check = routeMapper.checkToCheckEntity(assignment.getRoute());
        BusEntity busEntity = busMapper.busToBusEntity(assignment.getBus());

        return AssignmentEntity.builder()
                .id(assignment.getId())
                .tax(assignment.getTax())
                .journey(assignment.getJourney())
                .canceled(assignment.getCanceled())
                .route(check)
                .bus(busEntity)
                .build();
    }
}
