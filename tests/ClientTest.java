import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Team Project -- ClientTest
 *
 * Tests for the Client class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class ClientTest {
    // Fields
    private ServerSocket serverSocket;
    private Thread serverThread;
    private Socket clientSocket;
    private PrintWriter serverOut;
    private BufferedReader serverIn;
    private String lastMessageSent;
    private boolean clientDisconnected = false;
    private int port = 4242;

    @Before
    public void setUp() throws Exception {
        serverSocket = new ServerSocket(port);
        serverThread = new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();
                serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String message;

                while ((message = serverIn.readLine()) != null) {
                    lastMessageSent = message;

                    if ("LOGOUT".equalsIgnoreCase(message)) {
                        sendMessage("You have logged out.");
                        clientDisconnected = true;
                        break;
                    } else if (message.isEmpty() || message == null) {
                        sendMessage("No command received.");
                    } else if ("ADD_FRIEND".equalsIgnoreCase(message)) {
                        sendMessage("Friend added successfully.");
                    } else {
                        sendMessage("Invalid command received.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stopServer();
            }
        });

        serverThread.start();
    }

    @After
    public void tearDown() throws Exception {
        stopServer();

        serverThread.join();
    }

    @Test
    public void testClientConnection() throws Exception {
        Client client = new Client("localhost", port);

        assertNotNull("Client socket should not be null after connection", client);
    }

    @Test
    public void testServerMessageHandling() throws Exception {
        Client client = new Client("localhost", port);

        sendMessage("Test message from server.");

        simulateUserInput(client, "LOGOUT");

        assertEquals("Server message should be received correctly", "Test message from server.", lastMessageSent);
    }

    @Test
    public void testInvalidHostConnection() {
        Client client = new Client("invalidHost", port);

        assertNull("Client should not connect to an invalid host", client);
    }

    @Test
    public void testClientLogout() throws Exception {
        Client client = new Client("localhost", port);

        simulateUserInput(client, "LOGOUT");

        assertTrue("Client should log out and disconnect", clientDisconnected);
    }

    @Test
    public void testInvalidInput() throws Exception {
        Client client = new Client("localhost", port);

        simulateUserInput(client, "INVALID_COMMAND");

        assertEquals("Server should handle invalid input", "Invalid command received.", lastMessageSent);
    }

    @Test
    public void testClientActionsAfterLogin() throws Exception {
        Client client = new Client("localhost", port);

        sendMessage("Login successful.");

        sendMessage("Available actions: ADD_FRIEND, LOGOUT");

        simulateUserInput(client, "ADD_FRIEND");

        assertEquals("Server should handle the ADD_FRIEND command", "Friend added successfully.", lastMessageSent);
    }

    @Test
    public void testEdgeCaseEmptyInput() throws Exception {
        Client client = new Client("localhost", port);

        simulateUserInput(client, "");

        assertEquals("Server should handle empty input", "No command received.", lastMessageSent);
    }

    @Test
    public void testNullInput() throws Exception {
        Client client = new Client("localhost", port);

        simulateUserInput(client, null);

        assertEquals("Server should handle null input gracefully", "No command received.", lastMessageSent);
    }

    private void simulateUserInput(Client client, String input) throws Exception {
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);

        writer.println(input);
    }

    private void sendMessage(String message) {
        if (serverOut != null) {
            serverOut.println(message);
        }
    }

    private void stopServer() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} // End of class