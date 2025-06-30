package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.enums.StatusLixeira;
import com.suaempresa.cidadeinteligente.util.Localizacao;

public class LixeiraInteligente {
    private final int id; // identificador da lixeira
    private double capLitros; // capacidade da lixeira em flutuante;
    private final Localizacao loc; // lugar onde a lixeira está.
    private double lixoAtual; // Quantidade de lixo na lixeira atualmente.
    private StatusLixeira status;

    public LixeiraInteligente(int id, Localizacao loc, double cap) {
        this.id = id;
        this.loc = loc;
        this.capLitros = cap;
        this.lixoAtual = 0.0;
        this.status = StatusLixeira.OK;
    }
}
