import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean happening = true;

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Port 1234 is now working");
        }

        while (happening) {
            new Thread (new ClientHandler (serverSocket.accept()))).start();
        }
        serverSocket.close();
    }
}