package com.suaempresa.cidadeinteligente.residuos.modelo;

import com.suaempresa.cidadeinteligente.residuos.api.Observer;
import com.suaempresa.cidadeinteligente.residuos.api.Subject;

import java.util.ArrayList;
import java.util.List;

/*
* Classe para controlar o lixo da cidade. Recebe informações da LixeiraInteligente e opera baseado no que recebeu.
* Implementar o mais cedo possível. Provavelmente, usará o padrão de projeto Observer, para delegar a ideia de avisar que
* uma lixeira está cheia para a classe LixeiraInteligente.
*
* TODO: Implementar a classe CentralDeControle como um Observer do padrão de projeto Observer.
* */
public class CentralDeControle implements Observer {
    private static final CentralDeControle instancia = new CentralDeControle();

    private CentralDeControle() {}

    public static CentralDeControle getInstancia() {return instancia;}

    private List<LixeiraInteligente> todasLixeiras = new ArrayList<>();
    private List<LixeiraInteligente> filaDeColeta = new ArrayList<>();

    public void registrarLixeira(LixeiraInteligente novaLixeira) {
        this.todasLixeiras.add(novaLixeira);
        novaLixeira.addObserver(this);

        // registrar que nova lixeira foi adicionada.
    }

    @Override
    public void update(Subject notificador) {
        if(notificador instanceof LixeiraInteligente) {
            LixeiraInteligente reportedLixeira = (LixeiraInteligente) notificador;

            if(!this.filaDeColeta.contains(reportedLixeira)) {
                System.out.println("ALERTA: Lixeira" + reportedLixeira.getId() + "está cheia!");
                this.filaDeColeta.add(reportedLixeira);
            }
            else{
                System.out.println("INFORMAR CENTRAL: Lixeira" + reportedLixeira.getId() + "já está na lista de coleta.");
            }
        }
    }

    public List<LixeiraInteligente> getFilaDeColeta() {
        return this.filaDeColeta;
    }

    public double getPercentualLixeirasCheias() {
        if(todasLixeiras.isEmpty()) return 0.0;
        return ((double) filaDeColeta.size() / todasLixeiras.size()) * 100.0;
    }

    public int getTotalLixeirasNoSistema() {
        return todasLixeiras.size();
    }

    public void imprimirRelatorioDeStatus() {
        System.out.println("--- RELATÓRIO DA CENTRAL ---");
        System.out.printf("Total de lixeiras monitoradas: %d%n", getTotalLixeirasNoSistema());
        System.out.printf("Lixeiras aguardando coleta: %d%n", filaDeColeta.size());
        System.out.printf("Percentual de lixeiras cheias: %.2f%%%n", getPercentualLixeirasCheias());
        System.out.println("----------------------------");
    }
}
