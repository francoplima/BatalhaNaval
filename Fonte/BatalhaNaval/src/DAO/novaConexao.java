/*
 
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class novaConexao {
    protected static Connection conexao;
    protected static Statement statement;
    protected static PreparedStatement prepStatement;
    protected static ResultSet resultSet;
    
    protected static boolean conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            // Deve ser alterado quando o banco versÃ£o final estiver pronto
            conexao = DriverManager.getConnection("jdbc:sqlite:Bnaval.db");
            statement = conexao.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            // criar um log em txt futuramente
            e.printStackTrace();
        }
        return false;
    }
    protected static boolean desconectar() {
        try {
            conexao.close();
            return true;
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * O mÃ©todo delete deve ser usado para deletar dados do banco.
     */
    protected static boolean delete(String sql) {
        if (!sql.contains("delete")) {
            return false;
        }
        conectar();
        boolean result = false;
        try {
            result = statement.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        desconectar();
        return result;
    }
    
    /**
     * Antes de executar qualquer select sql. <br>
     * Deve-se executar o mÃ©todo conectar() e somente apÃ³s terminar de pegar todos os dados deve-se utilizar o mÃ©todo desconectar.
     * Lembre-se ao inserir pesquisas com Strings deve-se concatenar o valor String entre aspas '' simples.
     */
    protected static ResultSet exec(String sql) {
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    /**
     * O mÃ©todo save deve ser usado para inserir instruÃ§Ãµes no banco.
     */
    protected static boolean save(String sql) {
        if (!sql.contains("insert")) {
            return false;
        }
        try {
            return statement.execute(sql);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;        
    }
}
