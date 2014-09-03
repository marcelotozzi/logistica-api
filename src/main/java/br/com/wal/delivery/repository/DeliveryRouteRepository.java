package br.com.wal.delivery.repository;

import br.com.wal.delivery.component.CostCalculator;
import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.exception.MappingException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.model.DeliveryRoute;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelotozzi on 02/09/14.
 */
@Repository
public class DeliveryRouteRepository {
    @Autowired
    private GraphDatabaseFactory graphDatabaseFactory;

    public QueryResult query(QueryRoute queryRoute) {
        GraphDatabaseService graphDb = graphDatabaseFactory.get();
        Transaction tx = graphDb.beginTx();
        QueryResult queryResult = new QueryResult();

        try {
            Label label = DynamicLabel.label("Point");

            Node nodeA = graphDb.findNodesByLabelAndProperty(label, "name", queryRoute.getOrigin()).iterator().next();
            Node nodeB = graphDb.findNodesByLabelAndProperty(label, "name", queryRoute.getDestination()).iterator().next();

            PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
                    PathExpanders.forTypeAndDirection(RelTypes.DISTANCE, Direction.BOTH), "km");

            WeightedPath path = finder.findSinglePath(nodeA, nodeB);

            List<String> routes = new ArrayList<>();

            for (Node n : path.nodes()) {
                routes.add(String.valueOf(n.getProperty("name")));
            }

            queryResult.setCost(CostCalculator.calculateCost(path.weight(), queryRoute.getAutonomy(), queryRoute.getLiter()));
            queryResult.setRoute(routes);

            tx.success();
        } catch (Exception e) {
            e.printStackTrace();
            tx.failure();
        } finally {
            tx.close();
        }

        return queryResult;
    }


    public void map(String token, DeliveryMap deliveryMap) throws MappingException {
        GraphDatabaseService graphDb = graphDatabaseFactory.get();
        Transaction tx = graphDb.beginTx();
        try {
            Node nodeDeliveryMap = node(graphDb, "token", token, "DeliveryMap");
            nodeDeliveryMap.setProperty("token", token);
            nodeDeliveryMap.setProperty("name", deliveryMap.getName());

            for (DeliveryRoute deliveryRoute : deliveryMap.getDeliveryRoutes()) {
                // Cria ponto de origem
                Node nodeOrigin = node(graphDb, "name", deliveryRoute.getOrigin(), "Point");
                nodeOrigin.setProperty("name", deliveryRoute.getOrigin());

                // Cria pont de destino
                Node nodeDestination = node(graphDb, "name", deliveryRoute.getDestination(), "Point");
                nodeDestination.setProperty("name", deliveryRoute.getDestination());

                // Criar distancia
                Relationship distanceRelationship = nodeOrigin.createRelationshipTo(nodeDestination, RelTypes.DISTANCE);
                distanceRelationship.setProperty("km", deliveryRoute.getDistance());

                // Relaciona com o mapa
                nodeDeliveryMap.createRelationshipTo(nodeOrigin, RelTypes.BELONGS);
                nodeDeliveryMap.createRelationshipTo(nodeDestination, RelTypes.BELONGS);
            }

            tx.success();
        } catch (Exception e) {
            tx.failure();
            throw new MappingException("Erro ao mapear malha", e);
        } finally {
            tx.close();
        }
    }

    private Node node(GraphDatabaseService graphDb, String attributeName, String value, String labelName) {
        Label label = DynamicLabel.label(labelName);

        ResourceIterator<Node> nodeIterator = graphDb.findNodesByLabelAndProperty(label, attributeName, value).iterator();
        Node node;

        if (nodeIterator.hasNext()) {
            node = nodeIterator.next();
        } else {
            node = graphDb.createNode(label);
        }

        node.addLabel(label);

        return node;
    }

    private static enum RelTypes implements RelationshipType {
        DISTANCE, BELONGS
    }
}
