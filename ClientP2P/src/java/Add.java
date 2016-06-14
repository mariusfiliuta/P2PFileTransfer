import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Add"})
public class Add extends HttpServlet {
    private String addressIp= "localhost";
    private int addressPort= 5555;
    Socket socket = null;
    @Override
    public void init() throws ServletException {
        try {
            socket = new Socket(addressIp, addressPort); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();
            if(line.substring(0,3).equals("Id:")){
                int id = Integer.parseInt(line.substring(line.indexOf("Id:")+3));
                ServerSocket serverSocket;
                serverSocket = new ServerSocket(id);
                Socket socket = null;
                new StartCon(serverSocket);
            }
        } catch (IOException ex) {
            Logger.getLogger(Add.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String button =  "<div align = \"center\"><form action=\"Add\" method=\"POST\">" +
                "<input type=\"text\" name=\"name\"><br>" +
                "<input type=\"text\" name=\"source\"><br>" +
                "<input type=\"submit\" value=\"Adauga\"/> </form></div>";
        out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Add</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align=\"center\">Adauga Fisier</h1>");
            out.println(button);
            out.println("</body>");
            out.println("</html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String source = request.getParameter("source");
        
        PrintWriter outs = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        outs.println("File:" + source + "Name:" + name);
        String line = in.readLine();
        String url = null;
        if(line.substring(0,3).equals("Id:")){
            int id = Integer.parseInt(line.substring(line.indexOf("Id:")+3,line.indexOf("Name:")));
            String nameF = line.substring(line.indexOf("Name:")+5);
            url = addressIp + ':' + 8084 + '/' + "ClientP2P" + '/' + "Save" + "?id=" + id + "&name=" + name;  
        }
        else {
            url = "Nu a putut fi recunoscut";
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        
        String button =  "<div align = \"center\"><form action=\"Add\" method=\"POST\">" +
                "<input type=\"text\" name=\"Nume Fisier\"><br>" +
                "<input type=\"text\" name=\"prenume\"><br>" +
                "</table>" +
                "<input type=\"submit\" value=\"Adauga\"/> </form></div>";
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Add</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1 align=\"center\">Fisierul poate fi downloadat de la urmatorul link:</h1>");
        out.println("<h2 align=\"center\">" + url + "</h2>");
        out.println("<h3 color=\"red\" align=\"center\">Va rugam nu inchideti pagina pentru a mentine conexiunea deschisa.</h3>");
        out.println("<h1 align=\"center\">Adauga alt fisier Fisier</h1>");
        out.println(button);
        out.println("</body>");
        out.println("</html>");
        
    }

}
