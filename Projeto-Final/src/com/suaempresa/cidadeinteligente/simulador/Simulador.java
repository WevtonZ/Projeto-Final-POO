package com.suaempresa.cidadeinteligente.simulador;

import com.suaempresa.cidadeinteligente.enums.TipoDeLixo;
import com.suaempresa.cidadeinteligente.residuos.modelo.CaminhaoDeColeta;
import com.suaempresa.cidadeinteligente.residuos.modelo.CentralDeControle;
import com.suaempresa.cidadeinteligente.residuos.modelo.LixeiraFactory;
import com.suaempresa.cidadeinteligente.residuos.modelo.LixeiraInteligente;
import com.suaempresa.cidadeinteligente.util.Localizacao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Iterator;


/**
 * Simulador para a SmartCitySim, simulador de cidade inteligente.
 * Módulo implementado: Lixeiras Inteligentes, onde o objetivo é otimizar o sistema de coleta de lixo de uma cidade.
 *
 * A entrada do sistema funciona por um arquivo de entrada, então é necessário entrar com um para rodá-lo corretamente.
 * A ideia do sistema é ser uma implementação das coisas aprendidas na matéria de Programação Orientada a Objetos, onde haviam
 * requisitos sobre como deveria ser o sistema.
 */
public class Simulador {

    // DECLARAÇÃO DOS ATRIBUTOS DA CLASSE.
    private static final List<LixeiraInteligente> todasAsLixeiras = new ArrayList<>();
    private static final List<CaminhaoDeColeta> frotaDeCaminhoes = new ArrayList<>();
    private static final CentralDeControle central = CentralDeControle.getInstancia(); //
    private static final Random geradorAleatorio = new Random();
    private static int turnoAtual = 1;

    // AQUI ESTÁ A MAIN, ONDE RODARÁ TUDO.
    // dá throw de InterruptedException para indicar o programa que é OK parar por algum tempo.
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.err.println("Erro: Forneça o caminho do arquivo de simulação como argumento.");
            System.out.println("Uso: java com.suaempresa.cidadeinteligente.simulador.Simulador <caminho_do_arquivo>");
            System.exit(1);
        }

        String caminhoArquivo = args[0];
        System.out.println("--- INICIANDO SIMULADOR A PARTIR DO ARQUIVO: " + caminhoArquivo + " ---");
        Thread.sleep(1500);

        try {
            List<String> linhas = Files.readAllLines(Paths.get(caminhoArquivo));
            for (String linha : linhas) {
                processarComando(linha);
                Thread.sleep(500);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de simulação: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("\n--- FIM DA SIMULAÇÃO ---");
    }

    // método para processar os comandos do arquivo de texto.
    private static void processarComando(String linha) throws InterruptedException {
        String linhaTratada = linha.trim();
        if (linhaTratada.isEmpty() || linhaTratada.startsWith("#")) {
            return;
        }

        String[] partes = linhaTratada.split("\\s+");
        String comando = partes[0].toUpperCase();

        switch (comando) {
            case "CRIAR_LIXEIRA":
                criarLixeira(partes);
                break;
            case "CRIAR_CAMINHAO":
                criarCaminhao(partes);
                break;
            case "ADICIONAR_LIXO":
                adicionarLixo(partes);
                break;
            case "PASSAR_TURNO":
                passarTurno();
                break;
            default:
                System.err.println("Erro de Comando: Comando desconhecido '" + comando + "' na linha: \"" + linha + "\"");
        }
    }

    // método para criar uma lixeira
    private static void criarLixeira(String[] partes) {
        if (partes.length != 3) {
            System.err.println("Erro de Sintaxe: O comando CRIAR_LIXEIRA espera 2 argumentos. Ex: CRIAR_LIXEIRA <capacidade> <tipo>");
            return;
        }
        try {
            double capacidade = Double.parseDouble(partes[1]);
            if (capacidade <= 0) {
                System.err.println("Erro de Valor: A capacidade da lixeira deve ser um número positivo. Valor fornecido: " + capacidade);
                return;
            }
            TipoDeLixo tipo = TipoDeLixo.valueOf(partes[2].toUpperCase()); //
            Localizacao loc = new Localizacao(geradorAleatorio.nextInt(100), geradorAleatorio.nextInt(100)); //
            LixeiraInteligente novaLixeira = LixeiraFactory.criarLixeira(loc, capacidade, tipo); //
            todasAsLixeiras.add(novaLixeira);
            central.registrarLixeira(novaLixeira);
            System.out.println("CRIADA: " + novaLixeira);

        } catch (NumberFormatException e) {
            System.err.println("Erro de Formato: A capacidade da lixeira deve ser um número. Valor fornecido: '" + partes[1] + "'");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de Tipo: Tipo de lixo inválido '" + partes[2] + "'. Use um dos tipos válidos (ex: ORGANICO, RECICLAVEL).");
        }
    }

    private static void criarCaminhao(String[] partes) {
        if (partes.length != 4) {
            System.err.println("Erro de Sintaxe: O comando CRIAR_CAMINHAO espera 3 argumentos. Ex: CRIAR_CAMINHAO <id> <capacidade> <tipo>");
            return;
        }
        try {
            int caminhaoId = Integer.parseInt(partes[1]);
            double capacidade = Double.parseDouble(partes[2]);
            if (capacidade <= 0) {
                System.err.println("Erro de Valor: A capacidade do caminhão deve ser um número positivo. Valor fornecido: " + capacidade);
                return;
            }
            TipoDeLixo tipo = TipoDeLixo.valueOf(partes[3].toUpperCase()); //

            CaminhaoDeColeta novoCaminhao = new CaminhaoDeColeta(caminhaoId, capacidade, central.getLocalizacaoGaragem(), tipo); //

            frotaDeCaminhoes.add(novoCaminhao);
            central.registrarCaminhao(novoCaminhao); //

        } catch (NumberFormatException e) {
            System.err.println("Erro de Formato: O ID e a capacidade do caminhão devem ser números. Valores fornecidos: '" + partes[1] + "', '" + partes[2] + "'");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de Tipo: Tipo de lixo inválido '" + partes[3] + "'. Use um dos tipos válidos (ex: ORGANICO, RECICLAVEL).");
        }
    }

    private static void adicionarLixo(String[] partes) {
        if (partes.length != 3) {
            System.err.println("Erro de Sintaxe: O comando ADICIONAR_LIXO espera 2 argumentos. Ex: ADICIONAR_LIXO <id_lixeira> <volume>");
            return;
        }
        try {
            int lixeiraId = Integer.parseInt(partes[1]);
            double volume = Double.parseDouble(partes[2]);

            if (volume <= 0) {
                System.err.println("Erro de Valor: O volume de lixo a ser adicionado deve ser positivo. Valor fornecido: " + volume);
                return;
            }

            Optional<LixeiraInteligente> lixeiraOpt = findLixeiraById(lixeiraId);

            if (lixeiraOpt.isPresent()) {
                LixeiraInteligente l = lixeiraOpt.get();
                System.out.printf("ADICIONANDO: %.2fL de lixo na lixeira ID %d%n", volume, lixeiraId);
                l.addLixo(volume); //
            } else {
                System.err.println("Erro de Referência: Nenhuma lixeira encontrada com o ID " + lixeiraId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro de Formato: O ID da lixeira e o volume devem ser números. Valores fornecidos: '" + partes[1] + "', '" + partes[2] + "'");
        }
    }

    private static void passarTurno() throws InterruptedException {
        System.out.println("\n=============== PROCESSANDO TURNO " + turnoAtual++ + " ===============");
        Thread.sleep(1000);

        central.despacharFrota(); //
        Thread.sleep(1000);

        Iterator<CaminhaoDeColeta> it = frotaDeCaminhoes.iterator();
        while (it.hasNext()) {
            CaminhaoDeColeta caminhao = it.next();
            caminhao.executarTurno(); //
        }
        Thread.sleep(1000);

        System.out.println();
        central.imprimirRelatorioDeStatus(); //
        System.out.println("=========================================\n");
        Thread.sleep(2000);
    }

    private static Optional<LixeiraInteligente> findLixeiraById(int id) {
        return todasAsLixeiras.stream().filter(l -> l.getId() == id).findFirst();
    }
}