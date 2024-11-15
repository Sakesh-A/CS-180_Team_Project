
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
        BufferedReader reader = null;
        PrintWriter writer = null;
        String input = null;
        UserDatabase db = new UserDatabase();
        try {
            reader = new BufferedReader (new InputStreamReader (client.getInputStream ()));
            writer = new PrintWriter (client.getOutputStream (), true);

            writer.println("Do you have an account?");
            writer.flush();
            //do you have an account?
            input = reader.readLine();

            if (input.equals("Yes")) {
                while (true) {
                    String username = reader.readLine();
                    String password = reader.readLine();
                    boolean contains = false;
                    for (User u: db.getUsers()) {
                        if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        System.out.println("Invalid credentials");
                    } else {
                        break;
                    }
                }
            } else {
                while (true) {
                    String username = reader.readLine();
                    String password = reader.readLine();
                    boolean privacy = Boolean.parseBoolean(reader.readLine()); //probably have to change later
                    User u = new User(username, password, privacy);
                    boolean addedUser = db.addUser(u);
                    if (addedUser == false) {
                        System.out.println("User with username already exists");
                    } else {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }


/*        while(true) {
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
        }*/
    }
}