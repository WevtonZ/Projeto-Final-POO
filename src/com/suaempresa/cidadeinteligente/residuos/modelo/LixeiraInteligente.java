package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.enums.StatusLixeira;
import com.suaempresa.cidadeinteligente.enums.TipoDeLixo;
import com.suaempresa.cidadeinteligente.residuos.api.Observer;
import com.suaempresa.cidadeinteligente.residuos.api.Subject;
import com.suaempresa.cidadeinteligente.util.Localizacao;

import java.util.ArrayList;
import java.util.List;

/*
* Classe contendo as Lixeiras Inteligentes, que notificam a central de controle quando uma lixeira com ID x está cheia, para
* assim ser realizado a coleta nela. O construtor dessa classe é do tipo privado em pacotes, ou seja, apenas classes do mesmo pacote
* podem acessar esse construtor. A ideia é que o usuário pode não saber quais IDs de lixeira ele já adicionou, então, por criar
* o construtor de forma privada, ele não precisa se preocupar em qual lixeira ele está adicionando, mas sim com a sua localização atual e
* a sua capacidade de armazenamento de lixo.
* */

public class LixeiraInteligente implements Subject {

    // atributos.
    private final TipoDeLixo tipo;
    private final int id; // identificador da lixeira
    private final double capLitros; // capacidade total da lixeira.
    private final Localizacao loc; // lugar onde a lixeira está localizada.
    private double lixoAtual; // Quantidade de lixo na lixeira atualmente.
    private StatusLixeira status; // Saber o estado atual da lixeira

    // Não há necessidade da lixeira saber quem tá observando ela, mas sim de saber que há observadores.
    private List<Observer> observers = new ArrayList<>();

    LixeiraInteligente(int id, Localizacao loc, double cap, TipoDeLixo tipo) {
        this.id = id;
        this.loc = loc;
        this.capLitros = cap;
        this.lixoAtual = 0.0;
        this.status = StatusLixeira.OK;
        this.tipo = tipo;
    }

    public void addLixo(double volume) {
        if(volume < 0) return;
        if(this.status == StatusLixeira.OK || this.status == StatusLixeira.EM_COLETA) {
            this.lixoAtual += volume;
            if(this.lixoAtual >= this.capLitros) {
                if(this.status != StatusLixeira.CHEIA){
                    this.status = StatusLixeira.CHEIA;
                    notifyObservers();
                }
            }
        }
    }

    public void esvaziar() {
        this.lixoAtual = 0.0;
        this.status = StatusLixeira.OK;
    }

    @Override
    public void addObserver(Observer observer) {
        if(observer != null && !observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer: new ArrayList<>(this.observers)) {
            observer.update(this);
        }
    }

    public int getId() {
        return id;
    }

    public double getCapLitros() {
        return capLitros;
    }

    public Localizacao getLoc() {
        return loc;
    }

    public double getLixoAtual() {
        return lixoAtual;
    }

    public StatusLixeira getStatus() {
        return status;
    }
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LixeiraInteligente that = (LixeiraInteligente) o;
        return id == that.id;
    }
}
