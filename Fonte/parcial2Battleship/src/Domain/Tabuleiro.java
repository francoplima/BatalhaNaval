/*
 Classe tabuleiro, servira para atualizar a tela game
 */
package Domain;

import java.util.Random;

/**
 *
 * @author Franco_2
 */
public class Tabuleiro {
    private Embarcacao matriz[][];
    private Jogador desafiante;
    private Jogador adversario;
    
    public Tabuleiro() throws Throwable{
        matriz = new Embarcacao[10][10];
        //imprime(setFrota());
        preencheMatriz();
        imprime2();
    }//chamada de teste

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
    
    private void setEmbarcacao(Embarcacao emb) {
        int linha=0, coluna=0, controle = emb.getTamanho();
        boolean posOk = false;
        linha = sorteia(10);
        coluna = sorteia(10-emb.getTamanho());
        imprime2();
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
    }
    
    private void preencheMatriz() throws Throwable {
        int[] controle = {1, 2, 3, 4, 5};
        
        int a=0, coluna=0, linha=0, embarc;
        while (a < Embarcacao.TOTAL_EMBARCACOES) {
            embarc = sorteia(Embarcacao.EMBARCACOES_DIFERENTES);
            if (embarc == Embarcacao.PORTAAVIAO && controle[0] != 0) {
                controle[0]--;
                setEmbarcacao(new Embarcacao(Embarcacao.PORTAAVIAO));
            } else if (embarc == Embarcacao.ENCOURACADO && controle[1] != 0) {
                controle[1]--;
                setEmbarcacao(new Embarcacao(Embarcacao.ENCOURACADO));
            } else if (embarc == Embarcacao.FRAGATA && controle[2] != 0) {
                controle[2]--;
                setEmbarcacao(new Embarcacao(Embarcacao.FRAGATA));
            } else if (embarc == Embarcacao.SUBMARINO && controle[3] != 0) {
                controle[3]--;
                setEmbarcacao(new Embarcacao(Embarcacao.SUBMARINO));
            } else if (embarc == Embarcacao.LANCHA && controle[4] != 0) {
                controle[4]--;
                setEmbarcacao(new Embarcacao(Embarcacao.LANCHA));
            }
            a++;
        }
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
}