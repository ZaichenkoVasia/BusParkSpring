package com.spring.model.service.mapper;

import com.spring.model.domain.Bus;
import com.spring.model.entity.BusEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class BusMapper {

    public Bus busEntityToBus(BusEntity busEntity) {

        if (Objects.isNull(busEntity)) {
            return null;
        }

        return Bus.builder()
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
        if (Objects.isNull(bus)) {
            return null;
        }

        return BusEntity.builder()
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
