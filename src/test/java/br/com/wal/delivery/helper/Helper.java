package br.com.wal.delivery.helper;

import br.com.wal.delivery.model.DeliveryMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelotozzi on 03/09/14.
 */
public abstract class Helper {

    static String read(String file) throws IOException {
        InputStream is = DeliveryMap.class.getResourceAsStream(file);
        return IOUtils.toString(is);
    }
}
