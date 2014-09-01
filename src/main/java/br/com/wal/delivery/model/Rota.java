package br.com.wal.delivery.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class Rota implements Serializable {
    private static final long serialVersionUID = -8829637955680290015L;
    private String origem;
    private String destino;
    @JsonProperty("km")
    private Integer distanciaEmKilometros;

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getDistanciaEmKilometros() {
        return distanciaEmKilometros;
    }

    public void setDistanciaEmKilometros(Integer distanciaEmKilometros) {
        this.distanciaEmKilometros = distanciaEmKilometros;
    }
}
