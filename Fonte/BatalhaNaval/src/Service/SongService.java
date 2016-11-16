package Service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.jlp;

public class SongService {
    public static void main(String[] args) throws JavaLayerException {
        String[] s = {"testando.mp3"};
        jlp a = jlp.createInstance(s);
        a.play();
    }
}

