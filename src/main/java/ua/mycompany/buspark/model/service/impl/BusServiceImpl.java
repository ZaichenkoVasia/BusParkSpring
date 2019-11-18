package ua.mycompany.buspark.model.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mycompany.buspark.model.domain.Bus;
import ua.mycompany.buspark.model.domain.User;
import ua.mycompany.buspark.model.domain.enums.Status;
import ua.mycompany.buspark.model.entity.BusEntity;
import ua.mycompany.buspark.model.entity.UserEntity;
import ua.mycompany.buspark.model.repository.BusRepository;
import ua.mycompany.buspark.model.service.BusService;
import ua.mycompany.buspark.model.service.exception.InvalidPaginatingException;
import ua.mycompany.buspark.model.service.mapper.BusMapper;
import ua.mycompany.buspark.model.service.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BCryptPasswordEncoder encoder;
    private final BusMapper busMapper;
    private final UserMapper userMapper;

    public BusServiceImpl(BusRepository busRepository, BCryptPasswordEncoder encoder,
                          BusMapper busMapper, UserMapper userMapper) {
        this.busRepository = busRepository;
        this.encoder = encoder;
        this.busMapper = busMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Bus> findAll(Integer currentPage, Integer recordsPerPage) {
        if (currentPage <= 0 || recordsPerPage <= 0) {
            log.error("Invalid pagination counters");
            throw new InvalidPaginatingException("Invalid pagination counters");
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
//        if(user == null){
//            log.warn("User is not exist");
//            throw new EntityNotFoundException("User is not exist");
//        }
//        UserEntity userEntity = userMapper.userToUserEntity(user);
//        BusEntity busEntity
//        return busMapper.busEntityToBus();
        return null;
    }

    @Override
    public List<Bus> findByStatus(Status status) {
        return null;
    }
}
