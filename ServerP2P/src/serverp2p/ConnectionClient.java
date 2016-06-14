package serverp2p;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionClient extends Thread{
    Socket socketClient;
    ClientInfo clientInfo;
    public ConnectionClient(Socket s){
        socketClient = s;
        start();
    }
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
            Clients.clients.add(new ClientInfo(socketClient));
            clientInfo = Clients.clients.get(Clients.clients.size()-1);
            out.println("Id:" + clientInfo.getId());
            System.out.println(clientInfo.getId() + " Connectat");
            while(true){
              try{
                if(socketClient.isClosed()){
                    Clients.clients.remove(clientInfo);
                    break;
                }
                System.out.println("Citeste");
                String line = in.readLine();
                // File:sursa Name:nume_fisier
                if(line.substring(0,5).equals("File:")){
                    String source = line.substring(line.indexOf("File:")+5,line.indexOf("Name:"));
                    String name = line.substring(line.indexOf("Name:")+5);
                    System.out.println("Nume:" + name + "Sursa:" + source);
                    int idx = Clients.clients.indexOf(clientInfo);
                    Clients.clients.get(idx).addFile(name, source);
                    out.println("Id:" + clientInfo.getId() + "Name:" + name);
                }
                // Id:idPeer Name:nume_fisier
                else if(line.substring(0,3).equals("Id:")){
                    int id = Integer.parseInt(line.substring(line.indexOf("Id:")+3,line.indexOf("Name:")));
                    String name = line.substring(line.indexOf("Name:")+5);
                    Socket socketHasFile = null;
                    ClientInfo cF = null;
                    for( ClientInfo cI:Clients.clients){
                        if(cI.getId()==id){
                            cF = cI;
                            socketHasFile = cI.getSocket();
                            break;
                        }
                    }
                    if(socketHasFile == null || cF.getFilesAvailable().get(name).length() == 0 ){
                        out.println("Url not active anymore.");
                        continue;
                    }
                    
                    String source = cF.getFilesAvailable().get(name);
                    System.out.println("Address:" + socketHasFile.getInetAddress().getHostAddress() + "Port:" + cF.getId() + "Name:" + name + "File:" + source);
                    out.println("Address:" + socketHasFile.getInetAddress().getHostAddress() + "Port:" + cF.getId() + "Name:" + name + "File:" + source);
                    
                }
              } catch (IOException e) {
                Clients.clients.remove(clientInfo);
                System.out.println("Disconnected");
                out.close();
                in.close();
              }
            }
        } catch (IOException ex) {
            Clients.clients.remove(clientInfo);
            System.out.println("Disconnected");
            Logger.getLogger(ConnectionClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
