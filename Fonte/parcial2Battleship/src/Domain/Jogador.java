package Domain;

/**
 *
 * @author Franco_2
 */
public class Jogador {
    private int id;
    private String nome;
    private String ip;
    private int pontuacao;
    
    public Jogador(String nome, String ip) {
        this.nome = nome;
        this.ip = ip;
    }
    
    public void somarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIp() {
        return ip;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    
}
