
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

            writer.println("Do you have an account? Enter yes or no");
            writer.flush();
            //do you have an account?
            input = reader.readLine();

            if (input.equals("yes")) {
                while (true) {
                    writer.println("Please enter your username");
                    writer.flush();
                    String username = reader.readLine();
                    writer.println("Please enter your password");
                    writer.flush();
                    String password = reader.readLine();
                    boolean contains = false;
                    for (User u: db.getUsers()) {
                        if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        writer.println("Invalid Credentials");
                        writer.flush();
                    } else {
                        writer.println("Success");
                        writer.flush();
                        break;
                    }
                }
            } else if (input.equals("no")){
                while (true) {
                    writer.println("Please enter your username");
                    writer.flush();
                    String username = reader.readLine();
                    writer.println("Please enter your password ");
                    writer.flush();
                    String password = reader.readLine();
                    writer.println("Please enter your privacy, enter true or false");
                    writer.flush();
                    boolean privacy = Boolean.parseBoolean(reader.readLine()); //probably have to change later

                    boolean success = true;
                    try {
                        User u = new User(username, password, privacy);
                        boolean addedUser = db.addUser(u);
                        if (addedUser == false) {
                            writer.println("User with username already exists");
                            writer.flush();
                        } else {
                            writer.println("Success");
                            writer.flush();
                            break;
                        }
                    } catch (BadException e){
                        writer.println("Invalid password");
                        writer.flush();
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