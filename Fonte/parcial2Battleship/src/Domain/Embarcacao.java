/*Classe embarcação:
 *Nome: {porta-aviões, Encouraçado, fragata, submarino, lancha}
 *Tamanho: o numero de espaços ocupados na matriz, {1..5}
 *Posição: coordenadas x,y correspondente na matriz
 *Vida: mede o numero de vezes que a embarcação ainda pode ser acertada, se 0 então 
 *foi destruida.
*/
package Domain;

/**
 *
 * @author Franco_2
 */
public class Embarcacao {
    public static final int PORTAAVIAO = 1;
    public static final int ENCOURACADO = 2;
    public static final int FRAGATA = 3;
    public static final int SUBMARINO = 4;
    public static final int LANCHA = 5;
    public static final int EMBARCACOES_DIFERENTES = 5;
    public static final int TOTAL_EMBARCACOES = 14;
    
    private String nome;
    private int tamanho;
    private int[] posicao;
    private int vida;
    
    public Embarcacao (int embarc) throws Throwable {
        if (embarc == PORTAAVIAO) {
           portaAvioes();
        } else if (embarc == ENCOURACADO) {
           encouracado();
        } else if (embarc == FRAGATA) {
            fragata();
        } else if (embarc == SUBMARINO) {
            submarino();
        } else if (embarc == LANCHA) {
            lancha();
        } else {
            finalize();
        }
    }
    
    private void portaAvioes() {
        this.nome = "porta-aviões";
        this.tamanho = 5;
        this.vida = 5;
        this.posicao = new int[tamanho];
    }
    
    private void encouracado() {
        this.nome = "encouraçado";
        this.tamanho = 4;
        this.vida = 4;
        this.posicao = new int[tamanho];
    }
    
    private void fragata(){
        this.nome = "fragata";
        this.tamanho = 3;
        this.vida = 3;
        this.posicao = new int[tamanho];
    }
    
    private void submarino(){
        this.nome = "submarino";
        this.tamanho = 2;
        this.vida = 2;
        this.posicao = new int[tamanho];
    }
    
    private void lancha(){
        this.nome = "lancha";
        this.tamanho = 1;
        this.vida = 1;
        this.posicao = new int[tamanho];
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int[] getPosicao() {
        return posicao;
    }

    public void setPosicao(int[] posicao) {
        this.posicao = posicao;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    
    
}
