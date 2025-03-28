import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Team Project -- Server
 *
 * A server that interacts with our database and client to manage our social media site
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class Server implements ServerInterface {
    private static final int PORT = 4242;
    private static UserDatabase userDatabase = new UserDatabase();
    private static final List<User> LOGGED_IN_USERS = new ArrayList<>();
    private static final Object LOGIN_LOCK = new Object();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started and waiting for client connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, userDatabase).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a user to the list of logged-in users, ensuring thread safety.
     */
    public static boolean addLoggedInUser(User user)
    {
        if(user == null){
            return false;
        }
        synchronized (LOGIN_LOCK) {
            for (User loggedInUser : LOGGED_IN_USERS) {
                if (loggedInUser.getUsername().equals(user.getUsername())) {
                    return false; // User is already logged in
                }
            }
            LOGGED_IN_USERS.add(user);
            return true;
        }
    }

    /**
     * Removes a user from the list of logged-in users, ensuring thread safety.
     */
    public static void removeLoggedInUser(User user) {
        synchronized (LOGIN_LOCK) {
            LOGGED_IN_USERS.remove(user);
        }
    }
}




