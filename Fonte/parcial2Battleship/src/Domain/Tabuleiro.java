/*
 Classe tabuleiro, servira para atualizar a tela game
 */
package Domain;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Franco_2
 */
public class Tabuleiro {
    public static final int AGUA = -1;
    public static final int ERRO = -2;
    public static final int LINHAS=10, COLUNAS=10;
    
    private EmbarcacaoImpl matriz[][];
    private ArrayList<Embarcacao> embarcacoes;
    private Jogador jogador1;
    private Jogador jogador2;
    
    public Tabuleiro() throws Throwable {
        preencheMatriz();
        imprime2();
    }
    
    public Tabuleiro(String nome, String ip) throws Throwable{
        matriz = new EmbarcacaoImpl[LINHAS][COLUNAS];
        embarcacoes = new ArrayList<>();
        //imprime(setFrota());
        preencheMatriz();
        imprime2();
        jogador1 = new Jogador(nome, ip);
    }//chamada de teste
    
    public int[] embarcacoesRestantes(){
        int[] numeroEmbarcacoes = new int[Embarcacao.EMBARCACOES_DIFERENTES];
        for (Embarcacao a : embarcacoes) {
            if (a.isAtivo()) {
                numeroEmbarcacoes[a.getId()-1]+=1;
            }
        }
        return numeroEmbarcacoes;
    }

    public int acertaBarco(int linha, int coluna) {
        if (this.matriz[linha][coluna] == null) {
            return AGUA;
        } else {
            int posAcerto = posicaoDoAcerto(linha, coluna);
            int pontosObtidos = this.matriz[linha][coluna].acertaPosicao(posAcerto);
            jogador1.somarPontos(pontosObtidos);
            return posAcerto+1;
        }
    }
    
    private int posicaoDoAcerto(int linha, int coluna) {
        final int cod = this.matriz[linha][coluna].getId();
        final int tamEmbarc = this.matriz[linha][coluna].getTamanho();
        int posIni = -1, posFim = 0;
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
    
    
    
    private void imprime2() {
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
    
    public void imprime(int x[][]){
        //mock method
        for (int j=0;j<10;j++){
            for (int i=0;i<10;i++){
                System.out.print ( x[i][j]);  
            }      
            System.out.print ("\n");
        }   
    }//imprime matriz
    
    /**
     * O método recebe como parâmetro o número máximo que deve ser retornado de modo randômico.
     */
    public int sorteia(int max) {
        Random rand = new Random();
        if (max == 1) {
            max++;
        }
        int r = rand.nextInt(max);
        if (r < 0) {
            return r*-1;
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
        while (a < totalEmbarcacoes) {
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
            a++;
        } 
    }
    
    public static String getEmbarcacoesNome(int cod) {
        return EmbarcacaoImpl.getNomeEmbarcacoes(cod);
    }
    
    public int[][] setFrota() throws Throwable {
        int tab[][] = new int[10][10];
        int count;
        int i,j;
        
        
        
        //setando  1 porta-aviões
        i = sorteia(5);
        j = sorteia(9);
            for(int x =i;x<=(i+5);x++){
                tab[x][j]=5;            
            }
        
        
        //setando 2 encouraçados
        count = 0;
        while(count<2){
        i = sorteia(6);
        j = sorteia(9);
        if(tab[i][j]==0){
            for(int y =i;y<(i+4);y++){
                if (tab[i][j]!=0){
                    if(count!=0){
                        count--;
                    }
                    break;
                }
            }
            for(int x =i;x<(i+4);x++){
                tab[x][j]=4;
                
            }
            count++;
        }
        }
    
         //setando 3 fragatas
        count = 0;
        while(count<3){
        i = sorteia(7);
        j = sorteia(9);
        if(tab[i][j]==0){
            for(int y =i;y<(i+3);y++){
                if (tab[i][j]!=0){
                    if(count!=0){
                        count--;
                    }
                    break;
                }
            }
            for(int x =i;x<(i+3);x++){
                tab[x][j]=3;
            }
           count++; 
        }
        }
        
         //setando 4 submarinos
        count = 0;
        while(count<4){
        i = sorteia(8);
        j = sorteia(9);
        if(tab[i][j]==0){
            for(int y =i;y<(i+2);y++){
                if (tab[i][j]!=0){
                    if(count!=0){
                        count--;
                    }
                    break;
                }
            }
            for(int x =i;x<(i+2);x++){
                tab[x][j]=2;
            }
           count++; 
        }
        }
        
         //setando 5 lanchas
        count = 0;
        while(count<5){
        i = sorteia(9);
        j = sorteia(9);
        if(tab[i][j]==0){
            for(int y =i;y<(i+1);y++){
                if (tab[i][j]!=0){
                    if(count!=0){
                        count--;
                    }
                    break;
                }
            }
            for(int x =i;x<(i+1);x++){
                tab[x][j]=1;
                
            }
            count++;
        }
        }
              
        return tab;
    }
    
    
    public int pontosJogador1() {
        return jogador1.getPontuacao();
    }
    public int pontosJogador2() {
        return jogador2.getPontuacao();
    }
    public String getNomeJogador1() {
        return jogador1.getNome();
    }
    public String getNomeJogador2() {
        return jogador2.getNome();
    }
    public String getIpJogador1() {
        return jogador1.getIp();
    }
    public String getIpJogador2() {
        return jogador2.getIp();
    }
}