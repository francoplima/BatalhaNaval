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
public class EmbarcacaoImpl implements Embarcacao {
    
    private static final String[] NOMES = {"Porta-Avião", "Encouraçado", "Fragata", "Submarino", "Lancha"};
    private static final boolean DISPONIVEL = true;
    private static final boolean INDISPONIVEL = false;
    
    private int id;
    private String nome;
    private int tamanho;
    private boolean[] posicao;
    private boolean ativo;
    
    public EmbarcacaoImpl (int embarc) throws Throwable {
        if (embarc == PORTAAVIAO_ID) {
           portaAvioes();
        } else if (embarc == ENCOURACADO_ID) {
           encouracado();
        } else if (embarc == FRAGATA_ID) {
            fragata();
        } else if (embarc == SUBMARINO_ID) {
            submarino();
        } else if (embarc == LANCHA_ID) {
            lancha();
        } else {
            finalize();
        }
        this.ativo = true;
        for (int a=0; a<this.posicao.length; a++) {
            this.posicao[a] = DISPONIVEL;
        }
    }
    
    private void portaAvioes() {
        this.id = PORTAAVIAO_ID;
        this.nome = NOMES[id];
        this.tamanho = PORTAAVIAO_TAMANHO;
        this.posicao = new boolean[tamanho];
    }
    
    private void encouracado() {
        this.id = ENCOURACADO_ID;
        this.nome = NOMES[id];
        this.tamanho = ENCOURACADO_TAMANHO;
        this.posicao = new boolean[tamanho];
    }
    
    private void fragata(){
        this.id = FRAGATA_ID;
        this.nome = NOMES[id];
        this.tamanho = FRAGATA_TAMANHO;
        this.posicao = new boolean[tamanho];
    }
    
    private void submarino(){
        this.id = SUBMARINO_ID;
        this.nome = NOMES[id];
        this.tamanho = SUBMARINO_TAMANHO;
        this.posicao = new boolean[tamanho];
    }
    
    private void lancha(){
        this.id = LANCHA_ID;
        this.nome = NOMES[this.id];
        this.tamanho = LANCHA_TAMANHO;
        this.posicao = new boolean[tamanho];
    }

    @Override
    public String getNome() {
        return nome;
    }
    
    public static String getNomeEmbarcacoes(int embarc) {
        if (embarc == PORTAAVIAO_ID) {
           return EmbarcacaoImpl.NOMES[PORTAAVIAO_ID];
        } else if (embarc == ENCOURACADO_ID) {
           return EmbarcacaoImpl.NOMES[ENCOURACADO_ID];
        } else if (embarc == FRAGATA_ID) {
            return EmbarcacaoImpl.NOMES[FRAGATA_ID];
        } else if (embarc == SUBMARINO_ID) {
            return EmbarcacaoImpl.NOMES[SUBMARINO_ID];
        } else if (embarc == LANCHA_ID) {
            return EmbarcacaoImpl.NOMES[LANCHA_ID];
        } else {
            return "Unknow";
        }
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public int acertaPosicao(int pos) {
        if (ativo == INDISPONIVEL) {
            return 0;
        } else {
            int posDisp = 0;
            if (posicao[pos] == DISPONIVEL) {
                posicao[pos] = INDISPONIVEL;
            }
            for (int a=0; a<posicao.length; a++) {
                if (posicao[a] == DISPONIVEL) {
                    posDisp++;
                }
            }
            if (posDisp == 0) {
                ativo = INDISPONIVEL;
                return 100;
            }
            return PONTUACAO_MAXIMA/(tamanho*2);
        }
    }
    
    @Override
    public void desativar() {
        this.ativo = false;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public boolean isAtivo() {
        return this.ativo;
    }
    
    
    
}
