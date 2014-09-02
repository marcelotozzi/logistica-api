package br.com.wal.delivery.business;

import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.repository.DeliveryMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Service
public class DeliveryMapBusiness {
    @Autowired
    private DeliveryMapRepository deliveryMapRepository;

    public String create(DeliveryMap deliveryMap) throws RepositoryException {
        return deliveryMapRepository.register(deliveryMap);
    }

    public DeliveryMap show(String token) throws RepositoryException {
        return deliveryMapRepository.show(token);
    }
}
