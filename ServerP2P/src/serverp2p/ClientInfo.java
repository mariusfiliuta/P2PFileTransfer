
package serverp2p;

import java.net.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ClientInfo {
    private int id;
    private Socket socket;
    private Map<String, String> filesAvailable;
    
    public ClientInfo(Socket s){
        Random ran = new Random();
        this.id = ran.nextInt(9999);
        this.socket = s;
        filesAvailable = new HashMap<String, String>();
    }
    
    public void addFile(String name, String file){
        filesAvailable.put(name, file);
    }

    
    public void deleteFile(String name, String file){
        filesAvailable.remove(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Map<String, String> getFilesAvailable() {
        return filesAvailable;
    }
    
}
