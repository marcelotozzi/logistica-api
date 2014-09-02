package br.com.wal.delivery.repository;

import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.helper.DeliveryMapHelper;
import br.com.wal.delivery.model.DeliveryMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DeliveryMapRepositoryTest {

    @Autowired
    private DeliveryMapRepository deliveryMapRepository;

    @Test
    public void itShouldRegisterDeliveryMap() throws RepositoryException, IOException {
        DeliveryMap deliveryMap = DeliveryMapHelper.deliveryMap();

        String deliveryMapId = deliveryMapRepository.register(deliveryMap);

        assertThat(deliveryMapId, is(notNullValue()));
    }

    @Test
    public void itShouldLoadADeliveryMap() throws RepositoryException, IOException {
        //GIVEN
        DeliveryMap deliveryMap = DeliveryMapHelper.deliveryMap();
        String deliveryMapToken = deliveryMapRepository.register(deliveryMap);

        //WHEN
        DeliveryMap deliveryMapLoaded = deliveryMapRepository.show(deliveryMapToken);

        //THEN
        assertThat(deliveryMapLoaded, is(notNullValue()));
        assertThat(deliveryMapLoaded.getName(), is(equalTo(deliveryMap.getName())));
        assertThat(deliveryMapLoaded.getDeliveryRoutes(), is(notNullValue()));
        assertThat(deliveryMapLoaded.getDeliveryRoutes().size(), is(equalTo(6)));
    }
}