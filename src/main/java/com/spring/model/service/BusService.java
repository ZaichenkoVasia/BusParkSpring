package com.spring.model.service;

import com.spring.model.domain.Bus;
import org.springframework.data.domain.Page;

public interface BusService {
    Bus findByCode(int code);

    Bus findByModel(String model);

    Bus findById(Long id);

    Page<Bus> view(int currentPage, int pageSize);

    void save(Bus bus);

    Long addBus(Integer code, String model, Double mileage, Double consumption, String status, String comments);

    void changeBus(Integer code, Double newMileage, Double newConsumption);
}
