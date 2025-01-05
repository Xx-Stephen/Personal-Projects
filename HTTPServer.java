import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HTTPServer{
public static void main(String[] args) throws Exception {
final ServerSocket server = new ServerSocket(8080);
System.out.println("Listening for connection on port 8080");
while(true){
    try(Socket client = server.accept()){
        Date today = new Date();
        String response = "HTTP/1.1 200 OK\r\n\r\n" + today.toString();
        client.getOutputStream().write(response.getBytes("UTF-8"));
            }
        }
    }
}