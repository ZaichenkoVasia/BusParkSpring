//package com.transportpark.model.service.impl;
//
//import com.transportpark.model.domain.Route;
//import com.transportpark.model.entity.RouteEntity;
//import com.transportpark.model.repositories.RouteRepository;
//import com.transportpark.model.service.mapper.RouteMapper;
//import org.junit.After;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.Assert.assertThat;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {RouteServiceImpl.class})
//public class RouteServiceImplTest {
//
//    private static final Route ROUTE = getRoute();
//
//    private static final RouteEntity ENTITY = new RouteEntity();
//
//    private static final List<RouteEntity> ROUTE_ENTITIES = Collections.singletonList(ENTITY);
//
//    private static final List<Route> ROUTES = Collections.singletonList(ROUTE);
//
//    @Rule
//    public final ExpectedException exception = ExpectedException.none();
//
//    @MockBean
//    private RouteRepository repository;
//
//    @MockBean
//    private RouteMapper mapper;
//
//    @Autowired
//    private RouteServiceImpl service;
//
//    @After
//    public void resetMock() {
//        reset(repository, mapper);
//    }
//
//    @Test
//    public void shouldReturnRouteById() {
//        when(repository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
//        when(mapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);
//        Route actual = service.findById(1L);
//
//        verify(repository).findById(anyLong());
//        verify(mapper).routeEntityToRoute(any(RouteEntity.class));
//
//        assertThat(actual, equalTo(ROUTE));
//    }
//
////    @Test
////    public void shouldThrowInvalidDataRuntimeExceptionExceptionWithEmptyBusInAddBus() {
////        exception.expect(InvalidDataRuntimeException.class);
////        exception.expectMessage("Invalid input bus data");
////        service.addBus(null);
////    }
//
////    @Test
////    public void shouldSaveBus() {
////        when(repository.findByCode(anyInt())).thenReturn(Optional.empty());
////        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(ROUTE);
////
////        Bus actual = Bus.builder().id(1L).code(1).mileage(100).consumption(10).build();
////        service.addBus(actual);
////
////        verify(repository).save(any());
////    }
////
////    @Test
////    public void shouldThrowInvalidDataRuntimeExceptionExceptionWithEmptyBusInChangeBus() {
////        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
////        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(ROUTE);
////        exception.expect(InvalidDataRuntimeException.class);
////        exception.expectMessage("Invalid input bus data");
////        service.changeBus(1, null, null);
////    }
////
////    @Test
////    public void shouldChangeBus() {
////        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
////        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(ROUTE);
////
////        Bus actual = Bus.builder().id(1L).code(1).mileage(500).consumption(50).build();
////        actual.setConsumption(10);
////        actual.setMileage(100);
////        service.changeBus(1, 100.0, 10.0);
////        assertThat(actual, equalTo(ROUTE));
////        verify(repository).save(any());
////    }
////
////    @Test
////    public void shouldReturnPagenationBus() {
////        PageRequest sortedByCode = PageRequest.of(0, 1, Sort.by("code"));
////        when(repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(ROUTE_ENTITIES, sortedByCode, 1));
////        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(ROUTE);
////
////        Page<Bus> pageBus = service.showPageList(1, 1);
////
////        Page<Bus> actualPageBus = new PageImpl<Bus>(ROUTES, sortedByCode, 1);
////        assertThat(pageBus, equalTo(actualPageBus));
////    }
//
//    private static Route getRoute() {
//        return Route.builder()
//                .id(1L)
//                .status(0)
//                .build();
//    }
//}