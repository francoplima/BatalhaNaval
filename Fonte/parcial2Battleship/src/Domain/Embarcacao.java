/*Classe embarcação:
Nome: {porta-aviões, Encouraçado, fragata, submarino, lancha}
Tamanho: o numero de espaços ocupados na matriz, {1..5}
Posição: coordenadas x,y correspondente na matriz
Vida: mede o numero de vezes que a embarcação ainda pode ser acertada,se 0 então 
foi destruida.
*/
package Domain;

/**
 *
 * @author Franco_2
 */
public class Embarcacao {
    private String nome;
    private int tamanho;
    private int posicao;
    private int vida;
    
    public Embarcacao portaAvioes(){
        Embarcacao ship = new Embarcacao();
        ship.nome = "porta-aviões";
        ship.tamanho = 5;
        ship.vida = 5;
        return ship;        
    }
    
    public Embarcacao encouracado(){
        Embarcacao ship = new Embarcacao();
        ship.nome = "encouraçado";
        ship.tamanho = 4;
        ship.vida = 4;
        return ship;        
    }
    
    public Embarcacao fragata(){
        Embarcacao ship = new Embarcacao();
        ship.nome = "fragata";
        ship.tamanho = 3;
        ship.vida = 3;
        return ship;        
    }
    
    public Embarcacao submarino(){
        Embarcacao ship = new Embarcacao();
        ship.nome = "submarino";
        ship.tamanho = 2;
        ship.vida = 2;
        return ship;        
    }
    
    public Embarcacao lancha(){
        Embarcacao ship = new Embarcacao();
        ship.nome = "lancha";
        ship.tamanho = 1;
        ship.vida = 1;
        return ship;        
    }
}
