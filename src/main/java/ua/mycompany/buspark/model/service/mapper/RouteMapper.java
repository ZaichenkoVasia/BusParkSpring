package ua.mycompany.buspark.model.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ua.mycompany.buspark.model.domain.Route;
import ua.mycompany.buspark.model.entity.RouteEntity;

@Mapper
@Component
public interface RouteMapper {
    RouteEntity routeToRouteEntity(Route route);

    Route routeEntityToRoute(RouteEntity routeEntity);
}
