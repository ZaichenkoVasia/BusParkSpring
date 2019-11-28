package com.spring.model.service.impl;

import com.spring.model.domain.Bus;
import com.spring.model.entity.BusEntity;
import com.spring.model.exception.EntityNotFoundRuntimeException;
import com.spring.model.exception.InvalidDataRuntimeException;
import com.spring.model.repositories.BusRepository;
import com.spring.model.service.BusService;
import com.spring.model.service.mapper.BusMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    @Override
    public Bus findByCode(int code) {
        return busMapper.busEntityToBus(busRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this code")));
    }

    @Override
    public Page<Bus> showPageList(int currentPage, int pageSize) {
        PageRequest sortedByCode = PageRequest.of(currentPage - 1, pageSize, Sort.by("code"));
        Page<BusEntity> allBusesEntity = busRepository.findAll(sortedByCode);
        List<Bus> result = allBusesEntity
                .stream()
                .map(busMapper::busEntityToBus)
                .collect(Collectors.toList());
        return new PageImpl<>(result, sortedByCode, countAllBuses());
    }

    private long countAllBuses() {
        return busRepository.count();
    }

    @Override
    public void addBus(Bus bus) {
        if(Objects.isNull(bus)){
            log.warn("Invalid input bus data");
            throw new InvalidDataRuntimeException("Invalid input bus data");
        }
        Optional<BusEntity> busEntity = busRepository.findByCode(bus.getCode());
        if (busEntity.isPresent()) {
            log.warn("Bus with this code is exist");
            throw new InvalidDataRuntimeException("Bus with this code is exist");
        }
        BusEntity entityBus = busMapper.busToBusEntity(bus);
        busRepository.save(entityBus);
    }

    @Override
    public void changeBus(Integer code, Double newMileage, Double newConsumption) {
        Bus bus = busMapper.busEntityToBus(busRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this code")));
        if (Objects.isNull(newMileage) || Objects.isNull(newConsumption)) {
            log.warn("Invalid input bus data");
            throw new InvalidDataRuntimeException("Invalid input bus data");
        }
        bus.setMileage(newMileage);
        bus.setConsumption(newConsumption);
        BusEntity busEntity = busMapper.busToBusEntity(bus);
        busRepository.save(busEntity);
    }
}
