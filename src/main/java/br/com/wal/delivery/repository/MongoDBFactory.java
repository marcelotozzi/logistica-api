package br.com.wal.delivery.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

/**
 * Created by marcelotozzi on 04/09/14.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MongoDBFactory {
    public DB getClient() throws UnknownHostException {
        MongoClientURI clientURI = new MongoClientURI("mongodb://usuario:user@ds035310.mongolab.com:35310/logistica");
        MongoClient client = new MongoClient(clientURI);
        return client.getDB("logistica");
    }
}
