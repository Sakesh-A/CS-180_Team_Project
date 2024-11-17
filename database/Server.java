import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 12345;
    private static UserDatabase userDatabase = new UserDatabase();
    private static final List<User> loggedInUsers = new ArrayList<>();
    private static final Object loginLock = new Object();

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
    public static boolean addLoggedInUser(User user) {
        synchronized (loginLock) {
            for (User loggedInUser : loggedInUsers) {
                if (loggedInUser.getUsername().equals(user.getUsername())) {
                    return false; // User is already logged in
                }
            }
            loggedInUsers.add(user);
            return true;
        }
    }

    /**
     * Removes a user from the list of logged-in users, ensuring thread safety.
     */
    public static void removeLoggedInUser(User user) {
        synchronized (loginLock) {
            loggedInUsers.remove(user);
        }
    }
}




