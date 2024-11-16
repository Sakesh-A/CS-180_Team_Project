import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
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

    private void handleCommunication() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String serverMessage = (String) in.readObject();
                System.out.println("Server: " + serverMessage);

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
