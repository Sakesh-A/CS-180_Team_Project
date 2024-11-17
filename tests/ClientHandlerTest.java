import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.Socket;

/**
 * Team Project -- ClientHandlerTest
 *
 * Tests for the ClientHandler class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class ClientHandlerTest {
    // Fields
    private ClientHandler clientHandler;
    private Socket mockSocket;
    private ByteArrayOutputStream outputStream;
    private ObjectOutputStream mockOut;
    private ByteArrayInputStream inputStream;
    private ObjectInputStream mockIn;
    private UserDatabase mockUserDatabase;
    private User mockUser;

    @Before
    public void setUp() throws IOException {
        mockSocket = new Socket();
        outputStream = new ByteArrayOutputStream();
        mockOut = new ObjectOutputStream(outputStream);
        inputStream = new ByteArrayInputStream(new byte[0]);
        mockIn = new ObjectInputStream(inputStream);
        mockUserDatabase = new UserDatabase();
        mockUser = new User("user", "password", true);

        clientHandler = new ClientHandler(mockSocket, mockUserDatabase) {
            @Override
            protected ObjectInputStream getInputStream() {
                return mockIn;
            }

            @Override
            protected ObjectOutputStream getOutputStream() {
                return mockOut;
            }
        };
    }

    @Test
    public void testClientLogin() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password");
        mockUserDatabase.addUser(mockUser);

        clientHandler.run();

        assertEquals("Login successful.", getOutputData());
    }

    @Test
    public void testCreateAccount() throws IOException, ClassNotFoundException {
        setInputData("CREATE_ACCOUNT", "newuser", "newpassword", "true");

        clientHandler.run();

        assertEquals("Account created successfully.", getOutputData());
    }

    @Test
    public void testAddFriend() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "ADD_FRIEND", "friend");

        User mockFriend = new User("friend", "friendpassword", true);
        mockUserDatabase.addUser(mockUser);
        mockUserDatabase.addUser(mockFriend);
        mockUser.addFriend(mockFriend);

        clientHandler.run();

        assertEquals("Friend added successfully.", getOutputData());
    }

    @Test
    public void testRemoveFriend() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "REMOVE_FRIEND", "friend");

        User mockFriend = new User("friend", "friendpassword", true);
        mockUserDatabase.addUser(mockUser);
        mockUserDatabase.addUser(mockFriend);
        mockUser.addFriend(mockFriend);

        clientHandler.run();

        assertEquals("Friend removed successfully.", getOutputData());
    }

    @Test
    public void testSendMessage() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "SEND_MESSAGE", "recipient", "Hello!");

        User mockRecipient = new User("recipient", "recipientpassword", true);
        mockUserDatabase.addUser(mockUser);
        mockUserDatabase.addUser(mockRecipient);
        mockUser.sendMessage(mockRecipient, "Hello!");

        clientHandler.run();

        assertEquals("Message sent successfully.", getOutputData());
    }

    @Test
    public void testLogout() throws IOException {
        setInputData("LOGIN", "user", "password", "LOGOUT");

        clientHandler.run();

        assertEquals("You have logged out.", getOutputData());
    }

    @Test
    public void testHandleInvalidAction() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "INVALID_ACTION");

        clientHandler.run();

        assertEquals("Invalid action.", getOutputData());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws IOException, ClassNotFoundException {
        setInputData("LOGIN", "user", "wrongpassword");

        clientHandler.run();

        assertEquals("Error: Invalid username or password.", getOutputData());
    }

    @Test
    public void testCreateAccountWithExistingUsername() throws IOException,
            ClassNotFoundException {
        setInputData("CREATE_ACCOUNT", "user", "newpassword", "true");
        mockUserDatabase.addUser(mockUser);

        clientHandler.run();

        assertEquals("Error: Username already exists.", getOutputData());
    }

    @Test
    public void testAddFriendWhenNotLoggedIn() throws IOException, ClassNotFoundException {
        setInputData("ADD_FRIEND", "friend");

        clientHandler.run();

        assertEquals("You must log in first.", getOutputData());
    }

    @Test
    public void testRemoveFriendWhenNotLoggedIn() throws IOException, ClassNotFoundException {
        setInputData("REMOVE_FRIEND", "friend");

        clientHandler.run();

        assertEquals("You must log in first.", getOutputData());
    }

    @Test
    public void testSendMessageWhenNotLoggedIn() throws IOException, ClassNotFoundException {
        setInputData("SEND_MESSAGE", "recipient", "Hello!");

        clientHandler.run();

        assertEquals("You must log in first.", getOutputData());
    }

    @Test
    public void testLogoutWhenNotLoggedIn() throws IOException {
        setInputData("LOGOUT");

        clientHandler.run();

        assertEquals("You have logged out.", getOutputData());
    }

    private void setInputData(String... inputs) {
        StringBuilder inputData = new StringBuilder();

        for (String input : inputs) {
            inputData.append(input).append("\n");
        }

        inputStream = new ByteArrayInputStream(inputData.toString().getBytes());
        mockIn = new ObjectInputStream(inputStream);
    }

    private String getOutputData() throws IOException {
        mockOut.flush();

        return outputStream.toString().trim();
    }
} // End of class