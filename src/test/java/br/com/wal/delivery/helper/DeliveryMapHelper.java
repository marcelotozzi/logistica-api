package br.com.wal.delivery.helper;

import br.com.wal.delivery.model.DeliveryMap;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class DeliveryMapHelper {

    /**
     * Cria objeto deliveryMap baseado no arquivo deliveryMap.json
     *
     * @return
     * @throws IOException
     */
    public static DeliveryMap deliveryMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(deliveryMapJson(), DeliveryMap.class);
    }

    public static String deliveryMapJson() throws IOException {
        return read("/json/deliveryMap.json");
    }

    private static String read(String file) throws IOException {
        InputStream is = DeliveryMap.class.getResourceAsStream(file);
        return StringUtils.trimAllWhitespace(IOUtils.toString(is));
    }


    public static String deliveryMapJsonWithoutName() throws IOException {
        return read("/json/deliveryMapWithoutName.json");
    }

    public static String deliveryMapJsonWithoutRoutes() throws IOException {
        return read("/json/deliveryMapWithoutRoutes.json");
    }
}
