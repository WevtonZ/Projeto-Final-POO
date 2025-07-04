package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.enums.StatusLixeira;
import com.suaempresa.cidadeinteligente.enums.TipoDeLixo;
import com.suaempresa.cidadeinteligente.residuos.api.Observer;
import com.suaempresa.cidadeinteligente.residuos.api.Subject;
import com.suaempresa.cidadeinteligente.util.Localizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe LixeiraInteligente, que tem como foco lidar com as lixeiras tecnológicas.
 * Usa o padrão de projeto Observer, junto com a CentralDeControle, para que a lixeira possa notificar a central que a
 * sua lixeira está cheia, e que ela deve despachar caminhões para a devida coleta.
 *
 * Implementa a interface Subject, do padrão Observer, que tem a função de notificação da central.
 */

public class LixeiraInteligente implements Subject {

    private final TipoDeLixo tipo;
    private final int id;
    private final double capLitros;
    private final Localizacao loc;
    private double lixoAtual;
    private StatusLixeira status;
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
        if (volume < 0) return;
        if (this.status == StatusLixeira.MANUTENCAO) {
            System.out.println("ATENÇÃO: Não é possível adicionar lixo na lixeira " + id + ", está em manutenção.");
            return;
        }
        if (this.status == StatusLixeira.OK || this.status == StatusLixeira.EM_COLETA) {
            this.lixoAtual += volume;
            if (this.lixoAtual >= this.capLitros) {
                this.lixoAtual = this.capLitros; // Trava no máximo
                if (this.status != StatusLixeira.CHEIA) {
                    this.status = StatusLixeira.CHEIA;
                    System.out.println("EVENTO: Lixeira " + id + " ficou cheia.");
                    notifyObservers();
                }
            }
        }
    }

    /**
     * Remover o lixo da lixeira quando for possível esvaziá-la
     */
    public void esvaziar() {
        this.lixoAtual = 0.0;
        this.status = StatusLixeira.OK;
    }

    public void iniciarColeta() {
        if(this.status == StatusLixeira.CHEIA) {
            this.status = StatusLixeira.EM_COLETA;
        }
    }

    public void reverterLixeira() {
        if(this.status == StatusLixeira.EM_COLETA){
            this.status = StatusLixeira.CHEIA;
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : new ArrayList<>(this.observers)) {
            observer.update(this);
        }
    }

    // Getters
    public int getId() { return id; }
    public double getCapLitros() { return capLitros; }
    public Localizacao getLoc() { return loc; }
    public double getLixoAtual() { return lixoAtual; }
    public StatusLixeira getStatus() { return status; }
    public TipoDeLixo getTipoDeLixo() { return tipo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LixeiraInteligente that = (LixeiraInteligente) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Lixeira ID: %d (%.2fL / %.2fL) - Status: %s",
                id, lixoAtual, capLitros, status);
    }
}