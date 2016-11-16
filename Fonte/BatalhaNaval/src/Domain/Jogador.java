/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.rmi.Remote;

/**
 *
 * @author guilh
 */
public interface Jogador {

    int getId();
    String getIp();
    String getNome();
    int getPontuacao();
    void setPontuacao(int pontuacao);
    void somarPontos(int pontos);
    
}
