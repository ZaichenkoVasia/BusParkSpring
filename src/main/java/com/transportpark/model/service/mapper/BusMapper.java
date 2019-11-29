package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.entity.BusEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusMapper {

    public Bus busEntityToBus(BusEntity busEntity) {

        return Objects.isNull(busEntity) ?
                null :
                Bus.builder()
                        .id(busEntity.getId())
                        .code(busEntity.getCode())
                        .model(busEntity.getModel())
                        .mileage(busEntity.getMileage())
                        .consumption(busEntity.getConsumption())
                        .status(busEntity.getStatus())
                        .comments(busEntity.getComments())
                        .build();
    }

    public BusEntity busToBusEntity(Bus bus) {

        return Objects.isNull(bus) ?
                null :
                BusEntity.builder()
                        .id(bus.getId())
                        .code(bus.getCode())
                        .model(bus.getModel())
                        .mileage(bus.getMileage())
                        .consumption(bus.getConsumption())
                        .status(bus.getStatus())
                        .comments(bus.getComments())
                        .build();
    }
}
