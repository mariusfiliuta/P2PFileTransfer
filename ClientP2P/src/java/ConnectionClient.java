import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionClient extends Thread{
    Socket socketClient;
    public ConnectionClient(Socket s){
        socketClient = s;
        start();
    }
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            OutputStream os = null;
            os = socketClient.getOutputStream();
            while(true){
              try{
                String line = in.readLine();
                // File:sursa Name:nume_fisier
                if(line.substring(0,5).equals("File:")){
                    String source = line.substring(line.indexOf("File:")+5,line.indexOf("Name:"));
                    String name = line.substring(line.indexOf("Name:")+5);
                    FileInputStream fis = null;
                    File myFile = new File (source);
                    byte [] mybytearray  = new byte [16*1024];
                    fis = new FileInputStream(myFile);
                    int count;
                    while((count = fis.read(mybytearray))>0){
                        os.write(mybytearray,0,count);
                    }
                    os.close();
                }
              } catch (IOException e) {
                os.close();
                in.close();
              }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
