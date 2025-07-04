package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.util.Localizacao;
import com.suaempresa.cidadeinteligente.enums.TipoDeLixo;

/*
* Classe fábrica, usando o padrão de projeto Factory Method, para construir uma instância da lixeira inteligente.
* A ideia aqui é encapsular a atribuição dos IDs de cada lixeira, pois temos um problema em relação a deixar isso para o usuário
* mexer, já que ele pode se confundir e adicionar uma lixeira que tem o mesmo ID que alguma outra já adicionada.
*
* */
public class LixeiraFactory {
    private static int proxId = 1;

    public static LixeiraInteligente criarLixeira(Localizacao loc, double cap, TipoDeLixo tipo) {
        return new LixeiraInteligente(proxId++, loc, cap, tipo);
    }
}
