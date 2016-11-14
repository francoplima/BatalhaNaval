/*
 Classe tabuleiro, servira para atualizar a tela game
 */
package Service;

import Domain.Embarcacao;
import Domain.EmbarcacaoImpl;
import Domain.Jogador;
import Domain.JogadorImpl;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Franco_2
 */
public class TabuleiroService implements Tabuleiro {
    
    private EmbarcacaoImpl matriz[][];
    private ArrayList<Embarcacao> embarcacoes;
    private Jogador jogador1;
    private Jogador jogador2;
   
    
    public TabuleiroService() throws Throwable {
        preencheMatriz();
        imprime();
    }
    
    public TabuleiroService(String nome) throws Throwable{
        matriz = new EmbarcacaoImpl[LINHAS][COLUNAS];
        embarcacoes = new ArrayList<>();
        //imprime(setFrota());
        preencheMatriz();
        imprime();
        jogador1 = new JogadorImpl(nome, InetAddress.getLocalHost().getHostAddress());
        jogador2 = new JogadorImpl("bot", "");
    }//chamada de teste
    
    @Override
    public int[] embarcacoesRestantes(){
        int[] numeroEmbarcacoes = new int[Embarcacao.EMBARCACOES_DIFERENTES];
        for (Embarcacao a : embarcacoes) {
            if (a.isAtivo()) {
                numeroEmbarcacoes[a.getId()]+=1;
            }
        }
        return numeroEmbarcacoes;
    }

    /**
     *
     * @param linha linha da matriz do jogo
     * @param coluna coluna da matriz do jogo
     * @param jogador jogador que está fazendo a jogada
     * @return valor que representa a posição da embarcação de acerto, ou água
     */
    @Override
    public int acertaBarco(int linha, int coluna, boolean jogador) {
        if (this.matriz[linha][coluna] == null) {
            return AGUA;
        } else if(! this.matriz[linha][coluna].isAtivo()){
            return NADA;
        } else {
            int posAcerto = posicaoDoAcerto(linha, coluna);
            int pontosObtidos = this.matriz[linha][coluna].acertaPosicao(posAcerto);
            if (jogador) {
                jogador1.somarPontos(pontosObtidos);
            } else {
                jogador2.somarPontos(pontosObtidos);
            }
            return posAcerto+1;
        }
    }
    
    private int posicaoDoAcerto(int linha, int coluna) {
        final int cod = this.matriz[linha][coluna].getId();
        final int tamEmbarc = this.matriz[linha][coluna].getTamanho();
        int posIni = -1;
        for (int a=0; a<COLUNAS; a++) {
            if (this.matriz[linha][a] != null) {
                if (this.matriz[linha][a].getId() == cod) {
                    if(posIni == -1) {
                        posIni = a;
                        a = COLUNAS;
                    }
                }
            }
        }
        return coluna-posIni;
    }
    
    
    
    private void imprime() {
        System.out.println("Matriz");
        for (int i=0;i<10;i++){ 
            System.out.print(i+ " ");
            for (int j=0;j<10;j++){
                if (this.matriz[i][j] != null) {
                    System.out.print(matriz[i][j].getTamanho() + " ");  
                } else {
                    System.out.print(0 + " ");
                }
            }      
            System.out.println("");
        }   
    }
    
    
    /**
     * O método recebe como parâmetro o número máximo que deve ser retornado de modo randômico.
     */
    @Override
    public int sorteia(int max) {
        Random rand = new Random();
        if (max == 1) {
            max++;
        }
        int r = rand.nextInt(max);
        if (r < 0) {
            return r*(-1);
        }
        return r;
    }//retorna numero randomico intervalo i
    
    private boolean posicoesValidas(int linha, int coluna, int tamanho) {
        for(int a=0; a<tamanho; a++, coluna++) {
            if (this.matriz[linha][coluna] != null) {
                return false;
            }
        }
        return true;
    }
    
    private Embarcacao setEmbarcacao(EmbarcacaoImpl emb) {
        int linha=0, coluna=0, controle = emb.getTamanho();
        boolean posOk = false;
        linha = sorteia(10);
        coluna = sorteia(10-emb.getTamanho());
        posOk = posicoesValidas(linha, coluna, emb.getTamanho());
        do {
            if (posOk == false) {
                linha = sorteia(10);
                coluna = sorteia(10-emb.getTamanho());
                posOk = posicoesValidas(linha, coluna, emb.getTamanho());
            } else {
                this.matriz[linha][coluna] = emb;
                coluna++;
                controle--;
            }
        } while(controle > 0);
        return emb;
    }
    
    private void preencheMatriz() throws Throwable {
        int[] controle = Embarcacao.NUMERO_EMBARCACOES;
        int totalEmbarcacoes = Embarcacao.TOTAL_EMBARCACOES;
    
        int a=0, coluna=0, linha=0, embarc;
        while (totalEmbarcacoes > 0) {
            embarc = sorteia(EmbarcacaoImpl.EMBARCACOES_DIFERENTES);
            if (embarc == Embarcacao.PORTAAVIAO_ID && controle[0] != 0) {
                controle[0]--;
                embarcacoes.add(setEmbarcacao(new EmbarcacaoImpl(Embarcacao.PORTAAVIAO_ID)));
            } else if (embarc == Embarcacao.ENCOURACADO_ID && controle[1] != 0) {
                controle[1]--;
                embarcacoes.add(setEmbarcacao(new EmbarcacaoImpl(Embarcacao.ENCOURACADO_ID)));
            } else if (embarc == Embarcacao.FRAGATA_ID && controle[2] != 0) {
                controle[2]--;
                embarcacoes.add(setEmbarcacao(new EmbarcacaoImpl(Embarcacao.FRAGATA_ID)));
            } else if (embarc == Embarcacao.SUBMARINO_ID && controle[3] != 0) {
                controle[3]--;
                embarcacoes.add(setEmbarcacao(new EmbarcacaoImpl(Embarcacao.SUBMARINO_ID)));
            } else if (embarc == Embarcacao.LANCHA_ID && controle[4] != 0) {
                controle[4]--;
                embarcacoes.add(setEmbarcacao(new EmbarcacaoImpl(Embarcacao.LANCHA_ID)));
            }
            totalEmbarcacoes--;
        } 
    }
    
    /**
     * Retorno do método.
     * @return  O método retorna um vetor de String com valores representando as posições acertadas e o valor do campo.<br>
     * O padrão da String é "linha_coluna_valorDoCampo".
     */
    @Override
    public ArrayList<String> getJogadasJogador2(int tentativas) {
        int linha = sorteia(Tabuleiro.LINHAS);
        int coluna = sorteia(Tabuleiro.COLUNAS);
        
        ArrayList<String> acertos = new ArrayList<>();
        
        while (tentativas > 0) {
            int valorAcerto = acertaBarco(linha, coluna, false);
            
            int maiorEmbarcacao=0;
            
            for (int a=0; a<Embarcacao.NUMERO_EMBARCACOES.length; a++) {
                if (maiorEmbarcacao < Embarcacao.NUMERO_EMBARCACOES[a]) {
                    maiorEmbarcacao = Embarcacao.NUMERO_EMBARCACOES[a];
                }
            }
            
            if (valorAcerto == Tabuleiro.AGUA) {
                acertos.add(new String(linha+"_"+coluna+"_"+"A"));
                tentativas--;
            } else {
                acertos.add(new String(linha+"_"+coluna+"_"+valorAcerto));
                boolean logicando = true;
                int valor;
                while(logicando) {
                    if (valorAcerto != 1) {
                        coluna-=valorAcerto-1;
                        valorAcerto = acertaBarco(linha, coluna, false);
                        acertos.add(new String(linha+"_"+coluna+"_"+valorAcerto));
                        if (valorAcerto == Tabuleiro.AGUA) {
                            logicando = false;
                        }
                        tentativas--;
                    }
                }
            }
        }
        return acertos;
    }
    
    
    public static String getEmbarcacoesNome(int cod) {
        return EmbarcacaoImpl.getNomeEmbarcacoes(cod);
    }
    
    @Override
    public int pontosJogador1() {
        return jogador1.getPontuacao();
    }
    @Override
    public int pontosJogador2() {
        return jogador2.getPontuacao();
    }
    @Override
    public String getNomeJogador1() {
        return jogador1.getNome();
    }
    @Override
    public String getNomeJogador2() {
        return jogador2.getNome();
    }
    @Override
    public String getIpJogador1() {
        return jogador1.getIp();
    }
    @Override
    public String getIpJogador2() {
        return jogador2.getIp();
    }
}