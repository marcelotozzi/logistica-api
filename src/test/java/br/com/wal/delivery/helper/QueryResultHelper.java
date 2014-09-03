package br.com.wal.delivery.helper;

import java.io.IOException;

/**
 * Created by marcelotozzi on 03/09/14.
 */
public class QueryResultHelper extends Helper {

    public static String resultBToAJSON() throws IOException {
        return read("/json/queryRouteResultBToA.json");
    }

    public static String resultBToEJSON() throws IOException {
        return read("/json/queryRouteResultBToE.json");
    }

    public static String resultCToBJSON() throws IOException {
        return read("/json/queryRouteResultCToB.json");
    }

    public static String resultAToDJSON() throws IOException {
        return read("/json/queryRouteResultAToD.json");
    }
}
