package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.entity.BusEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BusMapperTest {
    private static final BusEntity BUS_ENTITY = getBusEntity();

    private static final Bus BUS = getBus();

    private final BusMapper busMapper = new BusMapper();

    @Test
    public void shouldMapGoodEntityToGood() {
        BusEntity actual = busMapper.busToBusEntity(BUS);

        assertThat(actual.getId(), is(BUS_ENTITY.getId()));
        assertThat(actual.getCode(), is(BUS_ENTITY.getCode()));
        assertThat(actual.getModel(), is(BUS_ENTITY.getModel()));
        assertThat(actual.getMileage(), is(BUS_ENTITY.getMileage()));
        assertThat(actual.getConsumption(), is(BUS_ENTITY.getConsumption()));
        assertThat(actual.getComments(), is(BUS_ENTITY.getComments()));
        assertThat(actual.getStatus(), is(BUS_ENTITY.getStatus()));
    }

    @Test
    public void shouldMapGoodToGoodEntity() {
        Bus actual = busMapper.busEntityToBus(BUS_ENTITY);

        assertThat(actual.getId(), is(BUS.getId()));
        assertThat(actual.getCode(), is(BUS.getCode()));
        assertThat(actual.getModel(), is(BUS.getModel()));
        assertThat(actual.getMileage(), is(BUS.getMileage()));
        assertThat(actual.getConsumption(), is(BUS.getConsumption()));
        assertThat(actual.getComments(), is(BUS.getComments()));
        assertThat(actual.getStatus(), is(BUS.getStatus()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        BusEntity actual = busMapper.busToBusEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Bus actual = busMapper.busEntityToBus(null);

        assertThat(actual, is(nullValue()));
    }

    private static BusEntity getBusEntity() {
        return new BusEntity(1L, 1, "model", 100, 10,
                "status", "comments", null);
    }

    private static Bus getBus() {
        return Bus.builder()
                .id(1L)
                .code(1)
                .model("model")
                .mileage(100)
                .consumption(10)
                .status("status")
                .comments("comments")
                .build();
    }
}
