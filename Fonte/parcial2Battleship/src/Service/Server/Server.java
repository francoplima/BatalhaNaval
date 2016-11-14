package Service.Server;

import java.io.IOException;
import org.apache.xmlrpc.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        
        WebServer server = new WebServer(port);
        System.out.println("Iniciando servidor na porta: " + port);
        server.addHandler("Server", new ServerService(port));
        System.out.println("Adicionando serviço " + "Server");
        server.setParanoid(false);
    }
}



/*import java.rmi.Remote;

public class Server {
    public Server(String ip) {
	try {
            int port = Integer.parseInt(TabuleiroService.RMI_SERVER_PORT);
            RmiUtils.startRegistry(port);
            ServerService p =  new ServerService();
            RmiUtils.registerService(ip, port, TabuleiroService.RMI_SEND_INVITE, (Remote) p);
            RmiUtils.listRemoteServices(ip, port);
	}catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
	}
    }
    
	
    public static void main(String[] args) {
    	new Server("localhost");
    }
    
}*/
