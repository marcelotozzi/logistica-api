package br.com.wal.delivery.business;

import br.com.wal.delivery.exception.MappingException;
import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.repository.DeliveryMapRepository;
import br.com.wal.delivery.repository.DeliveryRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Service
public class DeliveryMapBusiness {
    @Autowired
    private DeliveryMapRepository deliveryMapRepository;
    @Autowired
    private DeliveryRouteRepository deliveryRouteRepository;


    public String create(DeliveryMap deliveryMap) throws RepositoryException, MappingException {

        String token = deliveryMapRepository.register(deliveryMap);

        deliveryRouteRepository.map(token, deliveryMap);

        return token;
    }

    public DeliveryMap show(String token) throws RepositoryException {
        return deliveryMapRepository.show(token);
    }
}
