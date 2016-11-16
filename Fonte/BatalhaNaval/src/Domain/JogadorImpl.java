package Domain;

/**
 *
 * @author Franco_2
 */
public class JogadorImpl implements Jogador {
    private int id;
    private String nome;
    private String ip;
    private int pontuacao;
    
    public JogadorImpl(String nome, String ip) {
        this.nome = nome;
        this.ip = ip;
    }
    
    @Override
    public void somarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    
}
