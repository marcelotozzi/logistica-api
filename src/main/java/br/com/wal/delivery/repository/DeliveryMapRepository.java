package br.com.wal.delivery.repository;

import br.com.wal.delivery.exception.RepositoryException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.model.DeliveryRoute;
import br.com.wal.delivery.repository.generator.TokenGenerator;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelotozzi on 01/09/14.
 */
@Repository
public class DeliveryMapRepository {
    private static final Logger LOGGER = Logger.getLogger(DeliveryMapRepository.class);

    @Autowired
    private MongoDBFactory mongoDBFactory;

    public String register(DeliveryMap deliveryMap) throws RepositoryException {
        DB db = null;

        try {
            db = mongoDBFactory.getClient();
            DBCollection mapCollection = db.getCollection("mapas");

            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(deliveryMap);

            String token = TokenGenerator.generate(20);
            BasicDBObject doc = (BasicDBObject) JSON.parse(value);
            doc.append("token", token);

            mapCollection.insert(doc);

            return token;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public DeliveryMap show(String deliveryMapToken) throws RepositoryException {
        DB db = null;

        try {
            db = mongoDBFactory.getClient();

            DBCollection collection = db.getCollection("mapas");

            BasicDBObject query = new BasicDBObject();
            query.append("token", deliveryMapToken);
            BasicDBObject map = (BasicDBObject) collection.findOne(query);

            DeliveryMap deliveryMap = new DeliveryMap();
            deliveryMap.setName((String) map.get("nome"));

            List<DeliveryRoute> routes = new ArrayList<>();

            List<BasicDBObject> docRoutes = (List<BasicDBObject>) map.get("rotas");

            for (BasicDBObject d : docRoutes) {
                DeliveryRoute e = new DeliveryRoute();

                e.setOrigin(d.getString("origem"));
                e.setDestination(d.getString("destino"));
                e.setDistance(d.getInt("km"));
                routes.add(e);
            }
            deliveryMap.setDeliveryRoutes(routes);

            return deliveryMap;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }
}