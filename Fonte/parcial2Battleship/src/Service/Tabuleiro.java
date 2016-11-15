package Service;

import Domain.Jogador;
import java.util.ArrayList;

/**
 *
 * @author guilh
 */
public interface Tabuleiro {
    final int AGUA = -1;
    final int COLUNAS = 10;
    final int ERRO = -2;
    final int LINHAS = 10;
    final int NADA = -2;

    /**
     *
     * @param linha linha da matriz do jogo
     * @param coluna coluna da matriz do jogo
     * @param jogador jogador que está fazendo a jogada
     * @return valor que representa a posição da embarcação de acerto, ou água
     */
    int acertaBarco(int linha, int coluna, boolean jogador);
    int[] embarcacoesRestantes();
    int numeroEmbarcacoesRestantes();
    String getIpJogador1();
    String getIpJogador2();
    String getNomeJogador1();
    String getNomeJogador2();
    int pontosJogador1();
    int pontosJogador2();
    Jogador vencedor();
    /**
     * O método recebe como parâmetro o número máximo que deve ser retornado de modo randômico.
     */
    int sorteia(int max);
    
    
    ArrayList<String> getJogadasJogador2(int tentativas);
    
}
