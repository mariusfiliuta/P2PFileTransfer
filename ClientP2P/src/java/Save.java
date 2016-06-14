
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Save extends HttpServlet {
    private String addressIp= "localhost";
    private int addressPort= 5555;
    private int size = 16*1024;
    private String fileDownload = "D:\\Workspace\\PAO\\Download";
    Socket socket = null;
    @Override
    public void init() throws ServletException {
        try {
            socket = new Socket(addressIp, addressPort); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();
            if(line.substring(0,3).equals("Id:")){
                int id = Integer.parseInt(line.substring(line.indexOf("Id:")+3));
            }
        } catch (IOException ex) {
            Logger.getLogger(Add.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        
        PrintWriter outs = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        outs.println("Id:" + id + "Name:" + name);
        String line = in.readLine();
        if(line.substring(0,8).equals("Address:")){
            String address = line.substring(line.indexOf("Address:")+8,line.indexOf("Port:"));
            int port = Integer.parseInt(line.substring(line.indexOf("Port:")+5,line.indexOf("Name:")));
            String nameF = line.substring(line.indexOf("Name:")+5,line.indexOf("File:"));
            String source = line.substring(line.indexOf("File:")+5);
            
            Socket socketFile = new Socket(address, port);
            PrintWriter out = new PrintWriter(socketFile.getOutputStream(), true);
            out.println("File:" + source + "Name:" + nameF);
            FileOutputStream fos = null;
            InputStream is = socketFile.getInputStream();
            byte[] mybytearray  = new byte[size];
            fos = new FileOutputStream(fileDownload+ "\\" + nameF);
            int count;
            while((count = is.read(mybytearray)) > 0){
                fos.write(mybytearray, 0, count);
            }
            fos.close();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Add</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1 align=\"center\">Fisier Downloadat</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
