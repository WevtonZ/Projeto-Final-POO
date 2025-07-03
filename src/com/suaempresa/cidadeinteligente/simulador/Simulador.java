package com.suaempresa.cidadeinteligente.simulador;

import com.suaempresa.cidadeinteligente.util.Localizacao;
import com.suaempresa.cidadeinteligente.residuos.modelo.CentralDeControle;
import com.suaempresa.cidadeinteligente.residuos.modelo.LixeiraFactory;
import com.suaempresa.cidadeinteligente.residuos.modelo.LixeiraInteligente;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulador {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando o sistema para analisar coletas de lixo.\n");

        CentralDeControle central = CentralDeControle.getInstancia();
        System.out.println("Central de Controle está em operação.");

        List<LixeiraInteligente> todasAsLixeiras = new ArrayList<>();

        System.out.println("\nInstalando lixeiras na cidade...");
        todasAsLixeiras.add(LixeiraFactory.criarLixeira(new Localizacao(10, 20), 500)); // Lixeira 1
        todasAsLixeiras.add(LixeiraFactory.criarLixeira(new Localizacao(80, 15), 400)); // Lixeira 2
        todasAsLixeiras.add(LixeiraFactory.criarLixeira(new Localizacao(42, 42), 600)); // Lixeira 3

        for (LixeiraInteligente lixeira : todasAsLixeiras) {
            central.registrarLixeira(lixeira);
        }
        System.out.println("----------------------------------------------------");

        int turnosDeSimulacao = 10;
        Random geradorAleatorio = new Random();
        System.out.println("\n--- INICIANDO SIMULAÇÃO DE " + turnosDeSimulacao + " TURNOS ---\n");
        Thread.sleep(1000);

        for (int turno = 1; turno <= turnosDeSimulacao; turno++) {
            System.out.println("=============== TURNO " + turno + " ===============");

            System.out.println("... Cidadãos utilizando as lixeiras ...");
            for (LixeiraInteligente lixeira : todasAsLixeiras) {
                double lixoAdicionado = geradorAleatorio.nextInt(200);
                lixeira.addLixo(lixoAdicionado);
            }
            Thread.sleep(1000);

            System.out.println("\n... Central verificando a necessidade de coleta ...");
            List<LixeiraInteligente> filaDeColeta = central.getFilaDeColeta();

            if (filaDeColeta.isEmpty()) {
                System.out.println("Nenhuma lixeira cheia. Nenhuma coleta necessária.");
            } else {
                System.out.println("Lixeiras na fila para coleta: " + filaDeColeta.size());

                Iterator<LixeiraInteligente> iteradorDaFila = filaDeColeta.iterator();
                while (iteradorDaFila.hasNext()) {
                    LixeiraInteligente lixeiraParaColetar = iteradorDaFila.next();

                    System.out.println("=> Despachando caminhão para a lixeira ID: " + lixeiraParaColetar.getId());

                    lixeiraParaColetar.esvaziar();
                    System.out.println("Lixeira ID: " + lixeiraParaColetar.getId() + " foi esvaziada.");

                    iteradorDaFila.remove();
                    System.out.println("Lixeira ID: " + lixeiraParaColetar.getId() + " removida da fila de coleta.");
                }
            }

            System.out.println("\n--- STATUS GERAL NO FIM DO TURNO " + turno + " ---");
            for (LixeiraInteligente lixeira : todasAsLixeiras) {
                System.out.println(lixeira);
            }
            System.out.println("=========================================\n");
            Thread.sleep(2000); // pausar porque ninguem é de ferro
        }

        System.out.println("--- SIMULAÇÃO FINALIZADA ---");
    }
}