package serverp2p;

import java.io.IOException;
import java.net.*;

public class ServerP2P {

    static int port = 5555;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while(true){
            System.out.println("Cauta");
            socket = serverSocket.accept();
            new ConnectionClient(socket);
        }
    }
    
}
