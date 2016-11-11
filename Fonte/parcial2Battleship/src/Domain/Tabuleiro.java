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
    private int matriz[][];
    private Jogador desafiante;
    private Jogador adversario;
    
public Tabuleiro(){   
    imprime(setFrota());
}//chamada de teste

public int[][] setMatriz(){
    int[][] m = new int[10][10];
    return m;
}//preenchimento da matriz 

    public void imprime(int x[][]){
        //mock method
        for (int j=0;j<10;j++){
            for (int i=0;i<10;i++){
                System.out.print ( x[i][j]);  
            }      
            System.out.print ("\n");
        }   
    }//imprime matriz
    
    public int sorteia(int i){
        Random rand = new Random();
        int r = rand.nextInt(i)+1;
        return r;  
    }//retorna numero randomico intervalo i
    
    public int[][] setFrota() {
        int tab[][] = setMatriz();
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