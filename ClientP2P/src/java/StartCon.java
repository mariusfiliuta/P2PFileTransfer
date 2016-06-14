
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartCon extends Thread{
    ServerSocket serverConnection;
    public StartCon(ServerSocket s){
        this.serverConnection = s;
        start();
    }
    public void run(){
        Socket socket = null;
        while(true){
          
            try {
                socket = serverConnection.accept();
                new ConnectionClient(socket);
            } catch (IOException ex) {
                Logger.getLogger(StartCon.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
