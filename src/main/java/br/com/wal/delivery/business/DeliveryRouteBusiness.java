package br.com.wal.delivery.business;

import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.repository.DeliveryRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by marcelotozzi on 02/09/14.
 */
@Service
public class DeliveryRouteBusiness {
    @Autowired
    private DeliveryRouteRepository deliveryRouteRepository;

    public QueryResult query(QueryRoute queryRoute) {
        return deliveryRouteRepository.query(queryRoute);
    }
}
