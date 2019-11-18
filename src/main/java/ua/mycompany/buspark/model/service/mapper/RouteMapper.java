package ua.mycompany.buspark.model.service.mapper;

import org.mapstruct.Mapper;
import ua.mycompany.buspark.model.domain.Route;
import ua.mycompany.buspark.model.entity.RouteEntity;

@Mapper
public interface RouteMapper {
    Route routeToRouteEntity(RouteEntity routeEntity);

    RouteEntity routeEntityToRoute(Route route);
}
