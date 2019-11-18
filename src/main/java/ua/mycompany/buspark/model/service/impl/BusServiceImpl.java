package ua.mycompany.buspark.model.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.domain.User;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.BusEntity;
import ua.mycompany.buspark.model.entity.UserEntity;
import ua.mycompany.buspark.model.repository.BusRepository;
import ua.mycompany.buspark.model.service.BusService;
import ua.mycompany.buspark.model.service.exception.EntityNotFoundRuntimeException;
import ua.mycompany.buspark.model.service.exception.IncorrectValueRuntimeException;
import ua.mycompany.buspark.model.service.exception.InvalidPaginatingRuntimeException;
import ua.mycompany.buspark.model.service.mapper.BusMapper;
import ua.mycompany.buspark.model.service.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final UserMapper userMapper;

    public BusServiceImpl(BusRepository busRepository, BusMapper busMapper, UserMapper userMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Bus> findAll(Integer currentPage, Integer recordsPerPage) {
        if (currentPage <= 0 || recordsPerPage <= 0) {
            log.error("Invalid pagination counters");
            throw new InvalidPaginatingRuntimeException("Invalid pagination counters");
        }
        PageRequest pageRequest = PageRequest.of(currentPage, recordsPerPage);
        Page<BusEntity> result = busRepository.findAll(pageRequest);

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(busMapper::busEntityToBus)
                .collect(Collectors.toList());
    }

    @Override
    public long showNumberOfRows() {
        return busRepository.count();
    }

    @Override
    public Bus findByUser(User user) {
        if (user == null) {
            log.warn("User is not exist");
            throw new EntityNotFoundException("User is not exist");
        }
        UserEntity userEntity = userMapper.userToUserEntity(user);
        Optional<BusEntity> busEntity = busRepository.findByUser(userEntity);

        BusEntity busEntityResult = busEntity.
                orElseThrow(() -> {
                    log.warn("Bus don't exist with this user");
                    throw new EntityNotFoundRuntimeException("Bus don't exist with this user");
                });
        return busMapper.busEntityToBus(busEntityResult);
    }

    @Override
    public List<Bus> findByStatus(Status status) {
        if (status == null) {
            log.warn("Status of bus is null");
            throw new IncorrectValueRuntimeException("Status of bus is null");
        }
        List<BusEntity> result = busRepository.findByStatus(status);
        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(busMapper::busEntityToBus)
                .collect(Collectors.toList());
    }
}
