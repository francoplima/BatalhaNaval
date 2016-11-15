package Service.Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class Cliente {
    public static final String SERVER_NAME = "parcial2";
    public static final String SERVER_PORT = ":8080/";
    public static String SERVER_URL = "http://";
    
    public Object invoke(String serverUrl, Vector params, String operation) {
        XmlRpcClient xmlrpc;
        Object result = null;
        try {
            xmlrpc = new XmlRpcClient(serverUrl);
            result = xmlrpc.execute(operation, params);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean desafiarJogador(String nomeDesafiante, String ipDesafiado) 
            throws MalformedURLException, XmlRpcException, IOException {
        Vector params = new Vector();
        params.addElement(SERVER_NAME);
        params.addElement(nomeDesafiante);
        params.addElement(ipDesafiado);
        String url = SERVER_URL+ipDesafiado+SERVER_PORT+SERVER_NAME;
        Object resultado = invoke(url, params, SERVER_NAME+".desafiando");
        System.out.println(resultado);
        return Boolean.valueOf(resultado.toString()).booleanValue();
    }
    
    public boolean desafiando(String nome, String ip) 
            throws MalformedURLException, XmlRpcException, IOException {
        String input = JOptionPane.showInputDialog(null, "O jogador " + nome + " está de desafiando para um jogo", "", JOptionPane.YES_NO_OPTION);
        if (input.equals(JOptionPane.YES_OPTION)) {
            return true;
        }
        return false;
    }
    
    /**
     * @param jogadas Este parâmetro é enviado com as jogadas feitas pelo jogador na rodada corrente
     */
    public void enviarJogada(String jogadas) {
        
    }
    
    public static void main(String[] args) {
        Cliente ss = new Cliente();
        try {
            if (ss.desafiarJogador("Guilherme", "127.0.0.1")) {
                JOptionPane.showMessageDialog(null, "Desafio Ace");
            }
        } catch (XmlRpcException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
