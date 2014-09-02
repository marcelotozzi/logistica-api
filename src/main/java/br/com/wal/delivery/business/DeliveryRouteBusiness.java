package br.com.wal.delivery.business;

import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by marcelotozzi on 02/09/14.
 */
@Service
public class DeliveryRouteBusiness {
    public QueryResult query(QueryRoute queryRoute) {
        QueryResult queryResult = new QueryResult();
        queryResult.setCost(BigDecimal.valueOf(6.25));
        queryResult.setRoute(Arrays.asList("A", "B", "D"));
        return queryResult;
    }
}
