/*
 
 */
package DAO;


public abstract class PartidaDAO extends novaConexao {
    
    public static boolean SalvarSingle (String nome1, String Ip1,int Pontuacao1,int Pontuacao2, int[][] matriz) {
        conectar();
        final String sql = "insert into Partida (nome1, Ip1, Pontuacao1,Pontuacao2) "
                         + "values(" + nome1 + ", " + Ip1 +", " + Pontuacao1 +", " 
                         + Pontuacao2 + ");"
                         + "insert into Matriz (Matriz) "
                         + "values ("+ matriz + ");";
        desconectar(); 
        return save(sql);
    }
    
    public static boolean SalvarMulti (String nome1, String nome2, String Ip1, String Ip2, int Pontuacao1,int Pontuacao2, int[][] matriz) {
        conectar();
        final String sql = "insert into Partida (nome1, Ip1, Pontuacao1,Pontuacao2) "
                         + "values(" + nome1 + "," + nome2 + ", " + Ip1 +", " + Ip2
                         +", " + Pontuacao1 +", " + Pontuacao2 + ")"
                         + "insert into Matriz (Matriz) "
                         + "values ("+ matriz + ");";
        desconectar();
        return save(sql);
    }
    
    
}