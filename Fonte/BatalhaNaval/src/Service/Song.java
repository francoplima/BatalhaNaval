package Service;

import javazoom.jl.player.jlp;

public class Song implements Runnable {
    public static final String[] ACEERTO = {"acerto.mp3"};
    public static final String[] RODADA_FINALIZADA = {"rodadaFinalizada.mp3"};
    public static final String[] JOGO_FINALIZADO = {"jogoFinalizado.mp3"};
    public static final String[] AGUA = {"agua.mp3"};
    
    private jlp player;
    
    public void tocar(String[] tipo) {
        player = jlp.createInstance(tipo);
        player.play();
    }
    
    @Override
    public void run() {
        
    }
}
