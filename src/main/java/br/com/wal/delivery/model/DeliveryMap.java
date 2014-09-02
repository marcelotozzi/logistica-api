package br.com.wal.delivery.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class DeliveryMap implements Serializable {
    private static final long serialVersionUID = 3740538991755562496L;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("rotas")
    private List<DeliveryRoute> deliveryRoutes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeliveryRoute> getDeliveryRoutes() {
        return deliveryRoutes;
    }

    public void setDeliveryRoutes(List<DeliveryRoute> deliveryRoutes) {
        this.deliveryRoutes = deliveryRoutes;
    }
}
