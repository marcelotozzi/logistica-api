package br.com.wal.delivery.repository;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by marcelotozzi on 03/09/14.
 */
@Component
public class GraphDatabaseFactory {
    private final String dbPath;
    private GraphDatabaseService graphDatabaseService;

    @Inject
    public GraphDatabaseFactory(@Value("${neo4j.dbPath}") String dbPath) {
        this.dbPath = dbPath;
    }

    @PostConstruct
    public void init() {
        graphDatabaseService = new org.neo4j.graphdb.factory.GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDatabaseService);
    }

    private void registerShutdownHook(final GraphDatabaseService graphDatabaseService) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                graphDatabaseService.shutdown();
            }
        }
        ));
    }

    public GraphDatabaseService get() {
        return graphDatabaseService;
    }
}
