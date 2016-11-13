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
    
    private static final int PORTAAVIAO_ID = 1;
    private static final int ENCOURACADO_ID = 2;
    private static final int FRAGATA_ID = 3;
    private static final int SUBMARINO_ID = 4;
    private static final int LANCHA_ID = 5;
    
    public static final int EMBARCACOES_DIFERENTES = 5;
    public static final int TOTAL_EMBARCACOES = 14;
    
    private int id;
    private String nome;
    private int tamanho;
    private boolean[] posicao;
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
        for (int a=0; a<this.posicao.length; a++) {
            this.posicao[a] = true;
        }
    }
    
    private void portaAvioes() {
        this.id = PORTAAVIAO_ID;
        this.nome = "porta-aviões";
        this.tamanho = 5;
        this.vida = 5;
        this.posicao = new boolean[tamanho];
    }
    
    private void encouracado() {
        this.id = ENCOURACADO_ID;
        this.nome = "encouraçado";
        this.tamanho = 4;
        this.vida = 4;
        this.posicao = new boolean[tamanho];
    }
    
    private void fragata(){
        this.id = FRAGATA_ID;
        this.nome = "fragata";
        this.tamanho = 3;
        this.vida = 3;
        this.posicao = new boolean[tamanho];
    }
    
    private void submarino(){
        this.id = SUBMARINO_ID;
        this.nome = "submarino";
        this.tamanho = 2;
        this.vida = 2;
        this.posicao = new boolean[tamanho];
    }
    
    private void lancha(){
        this.id = LANCHA_ID;
        this.nome = "lancha";
        this.tamanho = 1;
        this.vida = 1;
        this.posicao = new boolean[tamanho];
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean acertaPosicao(int pos) {
        if (this.posicao[pos] == true) {
            this.posicao[pos] = false;
            return true;
        }
        return false;
    }
    
    public int getId() {
        return this.id;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    
    
}
