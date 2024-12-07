import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

                serverIn = new BufferedReader(new InputStreamReader
                        (clientSocket.getInputStream()));

                String message;

                while ((message = serverIn.readLine()) != null) {
                    lastMessageSent = message;

                    if ("LOGOUT".equalsIgnoreCase(message)) {
                        sendMessage("You have logged out.");

                        clientDisconnected = true;

                        break;
                    } else if (message.isEmpty()) {
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
    public void testClientConnection() {
        try {
            Client client = new Client("localhost", port);

            assertNotNull("Client socket should not be null after connection",
                    client);
        } catch (Exception e) {
            fail("Exception during client connection: " + e.getMessage());
        }
    }

    @Test
    public void testServerMessageHandling() {
        try {
            Client client = new Client("localhost", port);

            sendMessage("Test message from server.");

            simulateUserInput(client, "LOGOUT");

            assertEquals("Server message should be received correctly",
                    "Test message from server.", lastMessageSent);
        } catch (Exception e) {
            fail("Exception during server message handling: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidHostConnection() {
        try {
            Client client = new Client("invalidHost", port);

            assertNull("Client should not connect to an invalid host", client);
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testClientLogout() {
        try {
            Client client = new Client("localhost", port);

            simulateUserInput(client, "LOGOUT");

            assertTrue("Client should log out and disconnect", clientDisconnected);
        } catch (Exception e) {
            fail("Exception during client logout: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidInput() {
        try {
            Client client = new Client("localhost", port);

            simulateUserInput(client, "INVALID_COMMAND");

            assertEquals("Server should handle invalid input",
                    "Invalid command received.", lastMessageSent);
        } catch (Exception e) {
            fail("Exception during invalid input test: " + e.getMessage());
        }
    }

    @Test
    public void testClientActionsAfterLogin() {
        try {
            Client client = new Client("localhost", port);

            sendMessage("Login successful.");

            sendMessage("Available actions: ADD_FRIEND, LOGOUT");

            simulateUserInput(client, "ADD_FRIEND");

            assertEquals("Server should handle the ADD_FRIEND command",
                    "Friend added successfully.", lastMessageSent);
        } catch (Exception e) {
            fail("Exception during client actions test: " + e.getMessage());
        }
    }

    @Test
    public void testEdgeCaseEmptyInput() {
        try {
            Client client = new Client("localhost", port);

            simulateUserInput(client, "");

            assertEquals("Server should handle empty input",
                    "No command received.", lastMessageSent);
        } catch (Exception e) {
            fail("Exception during empty input test: " + e.getMessage());
        }
    }

    @Test
    public void testNullInput() {
        try {
            Client client = new Client("localhost", port);

            simulateUserInput(client, null);

            assertEquals("Server should handle null input gracefully",
                    "No command received.", lastMessageSent);
        } catch (Exception e) {
            fail("Exception during null input test: " + e.getMessage());
        }
    }

    private void simulateUserInput(Client client, String input) {
        try {
            PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(),
                    true);

            if (input != null) {
                writer.println(input);
            }
        } catch (IOException e) {
            fail("Error simulating user input: " + e.getMessage());
        }
    }

    private void sendMessage(String message) {
        if (serverOut != null) {
            serverOut.println(message);
        }
    }

    private void stopServer() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }

            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} // End of class