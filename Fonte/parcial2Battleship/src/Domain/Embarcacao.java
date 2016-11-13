/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author guilh
 */
public interface Embarcacao {
    public static final int PORTAAVIAO = 1;
    public static final int ENCOURACADO = 2;
    public static final int FRAGATA = 3;
    public static final int SUBMARINO = 4;
    public static final int LANCHA = 5;
    
    public static final int PORTAAVIAO_ID = 1;
    public static final int ENCOURACADO_ID = 2;
    public static final int FRAGATA_ID = 3;
    public static final int SUBMARINO_ID = 4;
    public static final int LANCHA_ID = 5;
    
    public static final int[] NUMERO_EMBARCACOES = {PORTAAVIAO, ENCOURACADO, FRAGATA, SUBMARINO, LANCHA};
    
    
    public static final int EMBARCACOES_DIFERENTES = 5;
    public static final int TOTAL_EMBARCACOES = PORTAAVIAO * ENCOURACADO * FRAGATA * SUBMARINO * LANCHA;

    public int acertaPosicao(int pos);
    public int getId();
    public String getNome();
    public int getTamanho();
    public boolean isAtivo();
}
