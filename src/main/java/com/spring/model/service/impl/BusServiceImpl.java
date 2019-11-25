package com.spring.model.service.impl;

import com.spring.model.domain.Bus;
import com.spring.model.entity.BusEntity;
import com.spring.model.exception.EntityNotFoundRuntimeException;
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
    public Bus findByModel(String model) {
        return busMapper.busEntityToBus(busRepository.findByModel(model)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this model")));
    }

    @Override
    public Bus findById(Long id) {
        return busMapper.busEntityToBus(busRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this id")));
    }

    @Override
    public Page<Bus> view(int currentPage, int pageSize) {
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
    public void save(Bus bus) {
        busRepository.save(busMapper.busToBusEntity(bus));
    }

    @Override
    public Long addBus(Integer code, String model, Double mileage, Double consumption, String status, String comments) {
        Bus bus = new Bus();
        bus.setCode(code);
        bus.setModel(model);
        bus.setMileage(mileage);
        bus.setConsumption(consumption);
        bus.setStatus(status);
        bus.setComments(comments);
        Optional<BusEntity> busEntity = busRepository.findByCode(code);
        if (busEntity.isPresent()) {
            log.info("Bus " + code + " is exist");
            return -1L;
        } else {
            busEntity = busRepository.findByModel(model);
            if (busEntity.isPresent()) {
                log.info("Bus " + model + " is exist");
                return -2L;
            } else {
                log.info("Bus is alredy exist");
                BusEntity result = busRepository.save(busMapper.busToBusEntity(bus));
                return result.getId();
            }
        }
    }

    @Override
    public void changeBus(Integer code, Double newMileage, Double newConsumption) {
        Bus bus = busMapper.busEntityToBus(busRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this code")));

        if (newMileage != null) {
            bus.setMileage(newMileage);
        }
        if (newConsumption != null) {
            bus.setConsumption(newConsumption);
        }
        busRepository.save(busMapper.busToBusEntity(bus));
    }
}
