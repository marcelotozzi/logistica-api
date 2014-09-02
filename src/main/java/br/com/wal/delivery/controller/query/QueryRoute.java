package br.com.wal.delivery.controller.query;

import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by marcelotozzi on 02/09/14.
 */
public class QueryRoute {
    @JsonProperty("mapa")
    private String mapName;

    @JsonProperty("origem")
    private String origin;

    @JsonProperty("destino")
    private String destination;

    @JsonProperty("autonomia")
    private BigDecimal autonomy;

    @JsonProperty("litro")
    private BigDecimal liter;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(BigDecimal autonomy) {
        this.autonomy = autonomy;
    }

    public BigDecimal getLiter() {
        return liter;
    }

    public void setLiter(BigDecimal liter) {
        this.liter = liter;
    }
}
