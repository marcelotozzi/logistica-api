package br.com.wal.delivery.helper;

import br.com.wal.delivery.controller.query.QueryRoute;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryRouteHelper extends Helper {
    public static String routeAToDJSON() throws IOException {
        return read("/json/queryRouteAToD.json");
    }

    public static String routeWithoutAutonomyJSON() throws IOException {
        return read("/json/queryRouteWithoutAutonomy.json");
    }

    public static String routeWithoutDestinationJSON() throws IOException {
        return read("/json/queryRouteWithoutDestination.json");
    }

    public static String routeWithoutLiterJSON() throws IOException {
        return read("/json/queryRouteWithoutLiter.json");
    }

    public static String routeWithoutMapNameJSON() throws IOException {
        return read("/json/queryRouteWithoutMapName.json");
    }

    public static String routeWithoutOriginJSON() throws IOException {
        return read("/json/queryRouteWithoutOrigin.json");
    }

    public static QueryRoute routeAToD() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(routeAToDJSON(), QueryRoute.class);
    }

    public static String routeBToEJSON() throws IOException {
        return read("/json/queryRouteBToE.json");
    }

    public static String routeBToAJSON() throws IOException {
        return read("/json/queryRouteBToA.json");
    }

    public static String routeCToBJSON() throws IOException {
        return read("/json/queryRouteCToB.json");
    }
}