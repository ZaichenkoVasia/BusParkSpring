package ua.mycompany.buspark.model.service.mapper;

import org.mapstruct.Mapper;
import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.entity.BusEntity;

@Mapper
public interface BusMapper {
    Bus busEntityToBus(BusEntity busEntity);

    BusEntity busToBusEntity(Bus bus);
}
