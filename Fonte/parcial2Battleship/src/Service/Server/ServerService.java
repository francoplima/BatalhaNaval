package Service.Server;

import Domain.Jogador;
import java.util.HashMap;
import java.util.Random;

public class ServerService {
    public static int PORT;
    private Jogador jogadorEmEspera;
    
    protected class Jogo {
        int cod;
        private boolean jogando;
        Jogador jogador1, jogador2;
        public boolean isJogando() { return this.jogando;}
    }
    HashMap<Integer, Jogo> jogos;
    Random gerador = new Random();
    
    public ServerService(int port) {
        PORT = port;
        jogos = new HashMap<>();
    }
    
    public Integer desafiar(Jogador jogador) {
        if (jogadorEmEspera == null) {
            jogadorEmEspera = jogador;
        } else {
            boolean finish = false;
            Integer cod;
            while(finish = true) {
                cod = new Integer(gerador.nextInt());
                if(!jogos.containsKey(cod) && cod.equals(0)) {
                    finish = true;
                }
            }
            
        }
        return new Integer(gerador.nextInt());
    }
}
