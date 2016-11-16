package Domain;

public interface Jogador {
    public int getId();
    public String getIp();
    public String getNome();
    public int getPontuacao();
    public void setPontuacao(int pontuacao);
    public void somarPontos(int pontos);
    
}
