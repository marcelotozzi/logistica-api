package br.com.wal.delivery.repository;

import br.com.wal.delivery.component.CostCalculator;
import br.com.wal.delivery.controller.query.QueryResult;
import br.com.wal.delivery.controller.query.QueryRoute;
import br.com.wal.delivery.exception.MappingException;
import br.com.wal.delivery.model.DeliveryMap;
import br.com.wal.delivery.model.DeliveryRoute;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.graphdb.traversal.Traverser;
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
            Label labelDeliveryMap = DynamicLabel.label("DeliveryMap");
            Node map = graphDb.findNodesByLabelAndProperty(labelDeliveryMap, "name", queryRoute.getMapName()).iterator().next();

            Node originNode = loadPointNodeFrom(queryRoute.getOrigin(), graphDb, map);
            Node destinationNode = loadPointNodeFrom(queryRoute.getDestination(), graphDb, map);

            WeightedPath path = findBestPath(originNode, destinationNode);

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

    private WeightedPath findBestPath(Node originNode, Node destinationNode) {
        PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
                PathExpanders.forTypeAndDirection(RelTypes.DISTANCE, Direction.BOTH), "km");

        return finder.findSinglePath(originNode, destinationNode);
    }

    private Node loadPointNodeFrom(String pointName, GraphDatabaseService graphDb, Node deliveryMap) {
        TraversalDescription originTraversal = graphDb.traversalDescription()
                .breadthFirst().relationships(RelTypes.BELONGS, Direction.BOTH)
                .evaluator(Evaluators.excludeStartPosition()).evaluator(new PointNameEvaluator(pointName));

        Traverser originTraverser = originTraversal.traverse(deliveryMap);
        return originTraverser.nodes().iterator().next();
    }


    public void map(String token, DeliveryMap deliveryMap) throws MappingException {
        GraphDatabaseService graphDb = graphDatabaseFactory.get();
        Transaction tx = graphDb.beginTx();
        try {
            // Criar Mapa
            Node nodeDeliveryMap = createMapNode(graphDb, deliveryMap.getName());
            nodeDeliveryMap.setProperty("token", token);
            nodeDeliveryMap.setProperty("name", deliveryMap.getName());

            for (DeliveryRoute deliveryRoute : deliveryMap.getDeliveryRoutes()) {
                // Cria ponto de origem
                Node nodeOrigin = createPointNode(graphDb, deliveryRoute.getOrigin(), nodeDeliveryMap);
                nodeOrigin.setProperty("name", deliveryRoute.getOrigin());

                // Cria ponto de destino
                Node nodeDestination = createPointNode(graphDb, deliveryRoute.getDestination(), nodeDeliveryMap);
                nodeDestination.setProperty("name", deliveryRoute.getDestination());

                // Criar relação de distancia
                Relationship distanceRelationship = nodeOrigin.createRelationshipTo(nodeDestination, RelTypes.DISTANCE);
                distanceRelationship.setProperty("km", deliveryRoute.getDistance());

                // Relaciona pontos com o mapa
                if (!nodeOrigin.hasRelationship(RelTypes.BELONGS, Direction.BOTH)) {
                    nodeDeliveryMap.createRelationshipTo(nodeOrigin, RelTypes.BELONGS);
                }
                if (!nodeDestination.hasRelationship(RelTypes.BELONGS, Direction.BOTH)) {
                    nodeDeliveryMap.createRelationshipTo(nodeDestination, RelTypes.BELONGS);
                }
            }

            tx.success();
        } catch (Exception e) {
            tx.failure();
            throw new MappingException("Erro ao mapear malha", e);
        } finally {
            tx.close();
        }
    }

    private Node createPointNode(GraphDatabaseService graphDb, String name, Node belongsTo) {
        TraversalDescription originTraversal = graphDb
                .traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.BELONGS, Direction.BOTH)
                .evaluator(Evaluators.excludeStartPosition())
                .evaluator(new PointNameEvaluator(name));

        Traverser originTraverser = originTraversal.traverse(belongsTo);

        Node node;

        Label label = DynamicLabel.label("Point");

        if (originTraverser.iterator().hasNext()) {
            node = originTraverser.iterator().next().endNode();
        } else {
            node = graphDb.createNode(label);
        }

        return node;
    }

    private Node createMapNode(GraphDatabaseService graphDb, String name) {
        Label label = DynamicLabel.label("DeliveryMap");

        ResourceIterator<Node> nodeIterator = graphDb.findNodesByLabelAndProperty(label, "name", name).iterator();
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

    private class PointNameEvaluator implements Evaluator {
        private final String value;

        public PointNameEvaluator(String propertyValue) {
            this.value = propertyValue;
        }

        @Override
        public Evaluation evaluate(Path path) {
            if (path.length() == 0) {
                return Evaluation.EXCLUDE_AND_CONTINUE;
            }

            Label labelPoint = DynamicLabel.label("Point");

            Node endNode = path.endNode();
            if (endNode.hasLabel(labelPoint) &&
                    value.equals(endNode.getProperty("name"))) {
                return Evaluation.INCLUDE_AND_CONTINUE;

            }
            return Evaluation.EXCLUDE_AND_CONTINUE;
        }
    }
}