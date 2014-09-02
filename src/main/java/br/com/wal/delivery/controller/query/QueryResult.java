package br.com.wal.delivery.controller.query;

import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryResult {
    @JsonProperty("rota")
    private List<String> route;

    @JsonProperty("custo")
    private BigDecimal cost;

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
