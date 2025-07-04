package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.enums.StatusCaminhao;
import com.suaempresa.cidadeinteligente.enums.StatusLixeira;
import com.suaempresa.cidadeinteligente.enums.TipoDeLixo;
import com.suaempresa.cidadeinteligente.util.Localizacao;
import com.suaempresa.cidadeinteligente.veiculos.Veiculo;

import java.util.Optional;

/**
 * Classe CaminhãoDeColeta, com o objetivo de ser parte da CentralDeControle, despachado para coletar o lixo das lixeiras cheias
 * espalhadas pela cidade. Apenas pega a lixeira
 */

public class CaminhaoDeColeta extends Veiculo {
    private final double capMaxima;
    private double cargaAtual;
    private StatusCaminhao status;
    private TipoDeLixo tipo;

    private Rota rotaAtual;
    private int proximoPontoIndex;

    public CaminhaoDeColeta(int id, double capMaxima, Localizacao locGaragem, TipoDeLixo tipo) {
        super(id, locGaragem);
        this.capMaxima = capMaxima;
        this.status = StatusCaminhao.GARAGEM;
        this.tipo = tipo;
        this.cargaAtual = 0.0;
    }

    public void iniciarRota(Rota rota) {
        if (this.status == StatusCaminhao.GARAGEM && rota != null) {
            this.rotaAtual = rota;
            this.proximoPontoIndex = 0;
            this.status = StatusCaminhao.EM_ROTA;
            System.out.printf("CAMINHÃO %d: Iniciando rota com %d paradas.%n", this.id, rota.getNumeroDePontos());
        }
    }

    @Override
    public void executarTurno() {
        if (this.status != StatusCaminhao.EM_ROTA) {
            return;
        }

        Optional<LixeiraInteligente> proximaLixeiraOpt = getProximaLixeira();
        if (proximaLixeiraOpt.isPresent()) {
            LixeiraInteligente proximaLixeira = proximaLixeiraOpt.get();
            this.locAtual = proximaLixeira.getLoc();
            System.out.printf("CAMINHÃO %d: Chegou na lixeira ID %d para coleta.%n", this.id, proximaLixeira.getId());
            coletar(proximaLixeira);
            this.proximoPontoIndex++;
        } else {
            System.out.printf("CAMINHÃO %d: Rota finalizada.%n", this.id);
            voltarParaGaragem();
        }
    }

    /**
     * Método para coletar o lixo da lixeira. Coleta APENAS se o caminhão for capaz de coletar todo o lixo da lixeira.
     * @param lixeira Lixeira em coleta.
     */
    private void coletar(LixeiraInteligente lixeira) {
        if (this.tipo != lixeira.getTipoDeLixo()) {
            System.out.printf("AVISO: Caminhão %d (tipo %s) não pode coletar lixo da lixeira %d (tipo %s).%n",
                    this.id, this.tipo, lixeira.getId(), lixeira.getTipoDeLixo());
            return; // Cancela a coleta
        }
        lixeira.iniciarColeta();
        System.out.printf("LIXEIRA %d: Status alterado para EM_COLETA.%n", lixeira.getId());

        double lixoNaLixeira = lixeira.getLixoAtual();

        if (this.cargaAtual + lixoNaLixeira > this.capMaxima) {
            System.out.printf("AVISO: Caminhão %d (Cap. livre: %.2f) não tem espaço para coletar %.2fL da lixeira %d.%n",
                    this.id, (this.capMaxima - this.cargaAtual), lixoNaLixeira, lixeira.getId());

            System.out.printf("LIXEIRA %d: Coleta cancelada. Status revertido para CHEIA.%n", lixeira.getId());
            lixeira.reverterLixeira();
            return;
        }

        this.cargaAtual += lixoNaLixeira;
        lixeira.esvaziar();

        System.out.printf("CAMINHÃO %d: Coleta total da lixeira %d realizada com sucesso.%n", this.id, lixeira.getId());
        System.out.printf("CAMINHÃO %d: Carga atual: %.2f / %.2f Kg.%n", this.id, this.cargaAtual, this.capMaxima);
    }


    private void voltarParaGaragem() {
        this.status = StatusCaminhao.DESCARREGANDO;
        this.cargaAtual = 0;
        this.status = StatusCaminhao.GARAGEM;
        this.rotaAtual = null;
        System.out.printf("CAMINHÃO %d: Retornou à garagem e está disponível.%n", this.id);
    }

    private Optional<LixeiraInteligente> getProximaLixeira() {
        if (rotaAtual != null && proximoPontoIndex < rotaAtual.getPontosDeColeta().size()) {
            return Optional.of(rotaAtual.getPontosDeColeta().get(proximoPontoIndex));
        }
        return Optional.empty();
    }

    // Getters
    public int getId() { return id; }
    public StatusCaminhao getStatus() { return status; }
    public Localizacao getLocAtual() { return locAtual; }
}