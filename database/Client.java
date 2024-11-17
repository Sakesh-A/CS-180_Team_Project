import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Team Project -- Client
 *
 * Creates a simple client that interacts with the server. All input/output is done in the terminal
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class Client implements ClientInterface {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to the server.");

            handleCommunication();
        } catch (IOException e) {
            System.out.println("Could not connect to the server: " + e.getMessage());
        }
    }
// comment
    public void handleCommunication() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String serverMessage = (String) in.readObject();

                System.out.println("Server: " + serverMessage);
                if (serverMessage.substring(0,5).equals("Error") || serverMessage.equals("Account created successfully.")) {
                    serverMessage = (String) in.readObject();
                    System.out.println("Server: " + serverMessage);
                }

                if (serverMessage.equals("Login successful.")) {
                    System.out.println("Available actions: \n" +
                    "ADD_FRIEND\n" +
                    "REMOVE_FRIEND\n" +
                    "BLOCK_USER\n" +
                    "SEND_MESSAGE\n" +
                    "DELETE_MESSAGE\n" +
                    "SEARCH_USER\n" +
                    "VIEW_USER\n" +
                    "LOGOUT\n");
                }

                // Exit condition: Close connection if server requests it
                if ("You have logged out.".equalsIgnoreCase(serverMessage)) {
                    break;
                }

                // Send user input to the server
                System.out.print("Your input: ");
                String userInput = scanner.nextLine();
                out.writeObject(userInput);

                if ("LOGOUT".equalsIgnoreCase(userInput)) {
                    System.out.println("Logging out...");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection lost: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Disconnected from the server.");
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String host = "localhost"; // Replace with server address if needed
        int port = 12345; // Replace with the server's port

        new Client(host, port);
    }
}
