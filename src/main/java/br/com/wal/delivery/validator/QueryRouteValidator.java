package br.com.wal.delivery.validator;

import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.exception.InvalidQueryRouteException;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryRouteValidator {
    public static void validateQueryRoute(QueryRoute queryRoute) throws InvalidQueryRouteException {
        if (!isValid(queryRoute)) {
            throw new InvalidQueryRouteException("Query inválida");
        }
    }

    private static boolean isValid(QueryRoute queryRoute) {
        return (queryRoute != null &&
                hasMapNameValid(queryRoute) &&
                hasOriginValid(queryRoute) &&
                hasDestinationValid(queryRoute) &&
                hasAutonomyValid(queryRoute) &&
                hasLiterValid(queryRoute));
    }

    private static boolean hasLiterValid(QueryRoute queryRoute) {
        return queryRoute.getLiter() != null;
    }

    private static boolean hasAutonomyValid(QueryRoute queryRoute) {
        return queryRoute.getAutonomy() != null;
    }

    private static boolean hasDestinationValid(QueryRoute queryRoute) {
        return queryRoute.getDestination() != null &&
                !"".equals(queryRoute.getDestination());
    }

    private static boolean hasOriginValid(QueryRoute queryRoute) {
        return queryRoute.getOrigin() != null &&
                !"".equals(queryRoute.getOrigin());
    }

    private static boolean hasMapNameValid(QueryRoute queryRoute) {
        return queryRoute.getMapName() != null &&
                !"".equals(queryRoute.getMapName());
    }
}
