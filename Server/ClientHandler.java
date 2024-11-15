
import java.net.*;
import java.io.*;
import java.util.*;
;

class ClientHandler implements Runnable
{
    private Socket client;
    Scanner sc = new Scanner(System.in);
    String choice = "";
    boolean isPublic = true;

    public ClientHandler (Socket c) {
        client = c;
    }

    public void run () {
        BufferedReader clientIn = null;
        PrintWriter clientOut = null;
        String input = null;

        try {
            clientIn = new BufferedReader (new InputStreamReader (client.getInputStream ()));
            clientOut = new PrintWriter (client.getOutputStream (), true);
        } catch (Exception e) {
            e.printStackTrace ();
        } 
        
        while(true) {
            System.out.println("Do you have an account?");
            choice = sc.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                System.out.println("Enter in your Username:");
                String user = sc.nextLine();

                System.out.println("Enter in your Password:");
                String pass = sc.nextLine();

            } else if (choice.equalsIgnoreCase("no")) {
                System.out.println("Please enter a username:");
                String user = sc.nextLine();

                System.out.println("Please enter a password:");
                String pass = sc.nextLine();

                System.out.println("Would you like anybody to be able to view your profile?");
                choice = sc.nextLine();
                if (choice.equalsIgnoreCase("yes")) { 
                    boolean isPublic = true;
                } else if (choice.equalsIgnoreCase("no"))  {
                    boolean isPublic = false;
                }



            }

            try {
                input = clientIn.readLine ();
                clientOut.println (input);
            } catch (Exception e) {
                e.printStackTrace ();
            }

            break;
        } 

        try {
            clientIn.close ();
            clientOut.close ();
            client.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}