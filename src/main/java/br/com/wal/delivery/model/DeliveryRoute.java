package br.com.wal.delivery.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class DeliveryRoute implements Serializable {
    private static final long serialVersionUID = -8829637955680290015L;
    @JsonProperty("origem")
    private String origin;
    @JsonProperty("destino")
    private String destination;
    @JsonProperty("km")
    private Integer distance;

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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
