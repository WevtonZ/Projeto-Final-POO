package com.suaempresa.cidadeinteligente.residuos.modelo;

import java.util.List;

public class Rota {
    private final List<LixeiraInteligente> pontosDeColeta;

    public Rota(List<LixeiraInteligente> pontosDeColeta) {
        if(pontosDeColeta == null || pontosDeColeta.isEmpty()){
            throw new IllegalArgumentException("NAO PODE ROTA VAZIA!!!!");
        }
        this.pontosDeColeta = pontosDeColeta;
    }

    public List<LixeiraInteligente> getPontosDeColeta() {
        return pontosDeColeta;
    }

    public int getNumeroDePontos() {
        return pontosDeColeta.size();
    }
}
