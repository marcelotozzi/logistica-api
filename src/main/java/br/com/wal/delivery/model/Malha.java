package br.com.wal.delivery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class Malha implements Serializable {
    private static final long serialVersionUID = 3740538991755562496L;
    private String nome;
    private List<Rota> rotas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas;
    }
}
