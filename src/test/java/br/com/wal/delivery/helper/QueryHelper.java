package br.com.wal.delivery.helper;

import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.model.DeliveryMap;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryHelper {
    public static String queryRouteJSON() throws IOException {
        return read("/json/queryRoute.json");
    }

    private static String read(String file) throws IOException {
        InputStream is = DeliveryMap.class.getResourceAsStream(file);
        return IOUtils.toString(is);
    }

    public static String queryRouteResultJSON() throws IOException {
        return read("/json/queryRouteResult.json");
    }

    public static String queryRouteWithoutAutonomyJSON() throws IOException {
        return read("/json/queryRouteWithoutAutonomy.json");
    }

    public static String queryRouteWithoutDestinationJSON() throws IOException {
        return read("/json/queryRouteWithoutDestination.json");
    }

    public static String queryRouteWithoutLiterJSON() throws IOException {
        return read("/json/queryRouteWithoutLiter.json");
    }

    public static String queryRouteWithoutMapNameJSON() throws IOException {
        return read("/json/queryRouteWithoutMapName.json");
    }

    public static String queryRouteWithoutOriginJSON() throws IOException {
        return read("/json/queryRouteWithoutOrigin.json");
    }

    public static QueryRoute queryRoute() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(queryRouteJSON(), QueryRoute.class);
    }
}