package br.com.wal.delivery.helper;

import br.com.wal.delivery.model.Malha;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class MalhaHelper {

    /**
     * Cria objeto malha baseado no arquivo malha.json
     *
     * @return
     * @throws IOException
     */
    public static Malha malha() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(malhaJson(), Malha.class);
    }

    public static String malhaJson() throws IOException {
        return read("/json/malha.json");
    }

    private static String read(String file) throws IOException {
        InputStream is = Malha.class.getResourceAsStream(file);
        return IOUtils.toString(is);
    }


    public static String malhaJsonSemNome() throws IOException {
        return read("/json/malhaSemNome.json");
    }

    public static String malhaJsonSemRotas() throws IOException {
        return read("/json/malhaSemRota.json");
    }
}
