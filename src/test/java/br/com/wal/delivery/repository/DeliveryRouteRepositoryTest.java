package br.com.wal.delivery.repository;

import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.exception.MappingException;
import br.com.wal.delivery.helper.DeliveryMapHelper;
import br.com.wal.delivery.helper.QueryHelper;
import br.com.wal.delivery.model.DeliveryMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DeliveryRouteRepositoryTest {

    @Autowired
    private DeliveryRouteRepository deliveryRouteRepository;

    @Test
    public void itShouldMapADeliveryMap() throws MappingException, IOException {
        //GIVEN
        DeliveryMap deliveryMap = DeliveryMapHelper.deliveryMap();

        //WHEN
        deliveryRouteRepository.map("1REV324V3", deliveryMap);

        //THEN
        QueryRoute queryRoute = QueryHelper.queryRoute();
        QueryResult queryResult = deliveryRouteRepository.query(queryRoute);

        assertThat(queryResult, is(notNullValue()));
        assertThat(queryResult.getRoute(), is(equalTo(Arrays.asList("A", "B", "D"))));
        assertThat(queryResult.getCost(), is(equalTo(BigDecimal.valueOf(6.25))));
    }
}