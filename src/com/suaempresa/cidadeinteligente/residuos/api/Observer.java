package com.suaempresa.cidadeinteligente.residuos.api;

/*
* Interface do observador do padrão de projeto Observer
* Ideia: Implementar a CentralDeControle como um Observer
* */

public interface Observer {
    void update(Subject subject);
}
