package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.residuos.api.Observer;
import com.suaempresa.cidadeinteligente.residuos.api.Subject;
import com.suaempresa.cidadeinteligente.util.Localizacao;
import com.suaempresa.cidadeinteligente.enums.StatusCaminhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Classe da Central de Controle do sistema, implementando o padrão de projeto Observer, sendo a parte observadora.
 * Controla as lixeiras e os caminhões para despache e coleta de lixo. Contém também um gerador de rotas otimizadas para o caminhão,
 * onde ele pegará um caminho próximo do ótimo, pois o caminho ótimo é um problema difícil de resolver (Problema do Caixeiro Viajante).
 * A ideia foi usar uma heurística para pegar um caminho que já é bom o suficiente para os nossos propósitos.
 */
public class CentralDeControle implements Observer {
    private static final CentralDeControle instancia = new CentralDeControle();

    private List<LixeiraInteligente> todasLixeiras = new ArrayList<>();
    private List<LixeiraInteligente> filaDeColeta = new ArrayList<>();
    private List<CaminhaoDeColeta> frotaDeCaminhoes = new ArrayList<>();
    private final Localizacao localizacaoGaragem = new Localizacao(0, 0); // Garagem na origem (0,0)

    private CentralDeControle() {}

    public static CentralDeControle getInstancia() {return instancia;}

    public void registrarLixeira(LixeiraInteligente novaLixeira) {
        this.todasLixeiras.add(novaLixeira);
        novaLixeira.addObserver(this);
    }

    public void registrarCaminhao(CaminhaoDeColeta caminhao) {
        this.frotaDeCaminhoes.add(caminhao);
        System.out.printf("CENTRAL: Caminhão ID %d registrado na frota.%n", caminhao.getId());
    }

    @Override
    public void update(Subject notificador) {
        if(notificador instanceof LixeiraInteligente) {
            LixeiraInteligente reportedLixeira = (LixeiraInteligente) notificador;

            if(!this.filaDeColeta.contains(reportedLixeira)) {
                System.out.println("ALERTA CENTRAL: Lixeira " + reportedLixeira.getId() + " está cheia!");
                this.filaDeColeta.add(reportedLixeira);
            }
            else{
                System.out.println("INFO CENTRAL: Lixeira " + reportedLixeira.getId() + " já está na fila de coleta.");
            }
        }
    }

    public void despacharFrota() {
        if (filaDeColeta.isEmpty()) {
            return;
        }

        System.out.println("... Central despachando a frota ...");

        Optional<CaminhaoDeColeta> caminhaoDisponivelOpt = frotaDeCaminhoes.stream()
                .filter(c -> c.getStatus() == StatusCaminhao.GARAGEM)
                .findFirst();

        if (caminhaoDisponivelOpt.isPresent()) {
            CaminhaoDeColeta caminhao = caminhaoDisponivelOpt.get();
            System.out.printf("CENTRAL: Caminhão %d disponível. Calculando rota otimizada...%n", caminhao.getId());

            List<LixeiraInteligente> lixeirasParaVisitar = new ArrayList<>(this.filaDeColeta);
            Rota rotaOtimizada = criarRotaOtimizada(lixeirasParaVisitar, this.localizacaoGaragem);

            caminhao.iniciarRota(rotaOtimizada);
            this.filaDeColeta.clear();
        } else {
            System.out.println("CENTRAL: Nenhum caminhão disponível no momento para atender à demanda.");
        }
    }

    private Rota criarRotaOtimizada(List<LixeiraInteligente> lixeirasParaVisitar, Localizacao pontoInicial) {
        List<LixeiraInteligente> rotaFinal = new ArrayList<>();
        List<LixeiraInteligente> lixeirasRestantes = new ArrayList<>(lixeirasParaVisitar);
        Localizacao localizacaoAtual = pontoInicial;

        while (!lixeirasRestantes.isEmpty()) {
            LixeiraInteligente vizinhoMaisProximo = null;
            double menorDistancia = Double.MAX_VALUE;

            for (LixeiraInteligente candidata : lixeirasRestantes) {
                double distancia = localizacaoAtual.distanciaPara(candidata.getLoc());
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    vizinhoMaisProximo = candidata;
                }
            }

            if (vizinhoMaisProximo != null) {
                rotaFinal.add(vizinhoMaisProximo);
                lixeirasRestantes.remove(vizinhoMaisProximo);
                localizacaoAtual = vizinhoMaisProximo.getLoc();
            }
        }
        System.out.println("CENTRAL: Rota otimizada criada com " + rotaFinal.size() + " paradas.");
        return new Rota(rotaFinal);
    }

    public Localizacao getLocalizacaoGaragem() {
        return localizacaoGaragem;
    }

    public List<LixeiraInteligente> getFilaDeColeta() {
        return this.filaDeColeta;
    }

    public void imprimirRelatorioDeStatus() {
        System.out.println("--- RELATÓRIO DA CENTRAL ---");
        System.out.printf("Total de lixeiras monitoradas: %d%n", todasLixeiras.size());
        System.out.printf("Lixeiras aguardando coleta: %d%n", filaDeColeta.size());
        System.out.println("----------------------------");
    }
}