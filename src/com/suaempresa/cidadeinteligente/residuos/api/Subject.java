package com.suaempresa.cidadeinteligente.residuos.api;

/*
* Interface do observado do padrão de projeto Observer.
* */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
