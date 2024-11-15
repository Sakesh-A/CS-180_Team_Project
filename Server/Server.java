import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean happening = true;

        try
        {
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (happening)
            new Thread (new ClientHandler (serverSocket.accept())).start();

        serverSocket.close();
    }
}