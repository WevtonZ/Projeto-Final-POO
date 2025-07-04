package com.suaempresa.cidadeinteligente.veiculos;

import com.suaempresa.cidadeinteligente.util.Localizacao;

public abstract class Veiculo {
    protected final int id;
    protected Localizacao locAtual;
    protected Enum<?> status;

    public Veiculo(int id, Localizacao loc) {
        this.id = id;
        this.locAtual = loc;
    }

    public int getId() {
        return id;
    }

    public Localizacao getLocalizacao() {
        return locAtual;
    }

    public Enum<?> getStatus() {
        return status;
    }

    public abstract void executarTurno();
}
