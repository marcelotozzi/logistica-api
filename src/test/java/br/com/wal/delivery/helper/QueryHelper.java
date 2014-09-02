package br.com.wal.delivery.helper;

import br.com.wal.delivery.model.DeliveryMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryHelper {
    public static String queryRoute() throws IOException {
        return read("/json/queryRoute.json");
    }

    private static String read(String file) throws IOException {
        InputStream is = DeliveryMap.class.getResourceAsStream(file);
        return IOUtils.toString(is);
    }

    public static String queryRouteResult() throws IOException {
        return read("/json/queryRouteResult.json");
    }

    public static String queryRouteWithoutAutonomy() throws IOException {
        return read("/json/queryRouteWithoutAutonomy.json");
    }

    public static String queryRouteWithoutDestination() throws IOException {
        return read("/json/queryRouteWithoutDestination.json");
    }

    public static String queryRouteWithoutLiter() throws IOException {
        return read("/json/queryRouteWithoutLiter.json");
    }

    public static String queryRouteWithoutMapName() throws IOException {
        return read("/json/queryRouteWithoutMapName.json");
    }

    public static String queryRouteWithoutOrigin() throws IOException {
        return read("/json/queryRouteWithoutOrigin.json");
    }
}