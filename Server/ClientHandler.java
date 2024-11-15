import java.net.*;
import java.io.*;

class ClientHandler implements Runnable
{
    private Socket client;

    public ClientHandler (Socket c)
    {
        client = c;
    }

    public void run ()
    {
        BufferedReader clientIn = null;
        PrintWriter clientOut = null;
        String input = null;

        try
        {
            clientIn = new BufferedReader (new InputStreamReader (client.getInputStream ()));
            clientOut = new PrintWriter (client.getOutputStream (), true);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }

        do
        {
            try
            {
                input = clientIn.readLine ();
                clientOut.println (input);
            }
            catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
        while (! input.equals ("Bye"));

        try
        {
            clientIn.close ();
            clientOut.close ();
            client.close ();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
}