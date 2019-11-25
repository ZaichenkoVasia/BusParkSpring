package com.spring.model.service.mapper;

import com.spring.model.domain.Route;
import com.spring.model.entity.RouteEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteMapper {

    public Route checkEntityToCheck(RouteEntity routeEntity) {
        if (routeEntity == null) {
            return null;
        }

        return Route.builder()
                .id(routeEntity.getId())
                .registerTime(routeEntity.getRegisterTime())
                .status(routeEntity.getStatus())
                .build();
    }

    public RouteEntity checkToCheckEntity(Route route) {
        if (route == null) {
            return null;
        }

        return RouteEntity.builder()
                .id(route.getId())
                .registerTime(route.getRegisterTime())
                .status(route.getStatus())
                .build();
    }
}
