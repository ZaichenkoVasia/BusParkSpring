package com.transportpark.model.service;

import com.transportpark.model.domain.Bus;
import org.springframework.data.domain.Page;

public interface BusService {

    Bus findByCode(int code);

    Page<Bus> showPageList(int currentPage, int pageSize);

    void addBus(Bus bus);

    void changeBus(Integer code, Double newMileage, Double newConsumption);
}
