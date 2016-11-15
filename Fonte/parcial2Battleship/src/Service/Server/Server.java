package Service.Server;

import java.io.IOException;
import org.apache.xmlrpc.*;

public class Server {
    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer(ServerService.SERVER_PORT);
        System.out.println("Iniciando servidor na porta: " + ServerService.SERVER_PORT);
        server.addHandler(ServerService.SERVER_NAME, new ServerService());
        System.out.println("Adicionando serviço " + ServerService.SERVER_NAME);
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
