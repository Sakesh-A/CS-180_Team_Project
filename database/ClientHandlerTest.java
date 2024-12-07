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
<<<<<<< HEAD
    public void setUp() throws IOException, BadException {
        mockSocket = new Socket();
        outputStream = new ByteArrayOutputStream();
        mockOut = new ObjectOutputStream(outputStream);
        inputStream = new ByteArrayInputStream(new byte[0]);
        mockIn = new ObjectInputStream(inputStream);
        mockUserDatabase = new UserDatabase();
        mockUser = new User("user", "password", true);

        clientHandler = new ClientHandler(mockSocket, mockUserDatabase) {

            protected ObjectInputStream getInputStream() {
                return mockIn;
            }


            protected ObjectOutputStream getOutputStream() {
                return mockOut;
            }
        };
=======
    public void setUp() {
        try {
            mockSocket = new Socket();

            outputStream = new ByteArrayOutputStream();

            mockOut = new ObjectOutputStream(outputStream);

            inputStream = new ByteArrayInputStream(new byte[0]);

            mockIn = new ObjectInputStream(inputStream);

            mockUserDatabase = new UserDatabase();

            mockUser = new User("user", "password", true);

            clientHandler = new ClientHandler(mockSocket, mockUserDatabase) {
                protected ObjectInputStream getInputStream() {
                    return mockIn;
                }

                protected ObjectOutputStream getOutputStream() {
                    return mockOut;
                }
            };
        } catch (IOException | BadException e) {
            fail("Setup failed due to exception: " + e.getMessage());
        }
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055
    }

    @Test
    public void testClientLogin() {
        try {
            setInputData("LOGIN", "user", "password");

            mockUserDatabase.addUser(mockUser);

            clientHandler.run();

            assertEquals("Login successful.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testClientLogin: " + e.getMessage());
        }
    }

    @Test
    public void testCreateAccount() {
        try {
            setInputData("CREATE_ACCOUNT", "newuser", "newpassword", "true");

            clientHandler.run();

            assertEquals("Account created successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testCreateAccount: " + e.getMessage());
        }
    }

    @Test
<<<<<<< HEAD
    public void testAddFriend() throws IOException, BadException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "ADD_FRIEND", "friend");
=======
    public void testAddFriend() {
        try {
            setInputData("LOGIN", "user", "password", "ADD_FRIEND", "friend");
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            User mockFriend = new User("friend", "friendpassword",
                    true);

            mockUserDatabase.addUser(mockUser);

            mockUserDatabase.addUser(mockFriend);

            clientHandler.run();

            assertEquals("Friend added successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testAddFriend: " + e.getMessage());
        }
    }

    @Test
<<<<<<< HEAD
    public void testRemoveFriend() throws IOException, BadException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "REMOVE_FRIEND", "friend");
=======
    public void testRemoveFriend() {
        try {
            setInputData("LOGIN", "user", "password", "REMOVE_FRIEND", "friend");
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            User mockFriend = new User("friend", "friendpassword",
                    true);

            mockUserDatabase.addUser(mockUser);

            mockUserDatabase.addUser(mockFriend);

            mockUser.addFriend(mockFriend);

            clientHandler.run();

            assertEquals("Friend removed successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testRemoveFriend: " + e.getMessage());
        }
    }

    @Test
<<<<<<< HEAD
    public void testBlockUser() throws IOException, BadException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "BLOCK_USER", "blockedUser");
=======
    public void testBlockUser() {
        try {
            setInputData("LOGIN", "user", "password", "BLOCK_USER", "blockedUser");
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            User mockBlockedUser = new User("blockedUser",
                    "blockedpassword", true);

            mockUserDatabase.addUser(mockUser);

            mockUserDatabase.addUser(mockBlockedUser);

            clientHandler.run();

            assertEquals("User blocked successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testBlockUser: " + e.getMessage());
        }
    }

    @Test
<<<<<<< HEAD
    public void testSendMessage() throws IOException, BadException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "SEND_MESSAGE", "recipient", "Hello!");
=======
    public void testSendMessage() {
        try {
            setInputData("LOGIN", "user", "password", "SEND_MESSAGE", "recipient",
                    "Hello!");
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            User mockRecipient = new User("recipient",
                    "recipientpassword", true);

            mockUserDatabase.addUser(mockUser);

            mockUserDatabase.addUser(mockRecipient);

            clientHandler.run();

            assertEquals("Message sent successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testSendMessage: " + e.getMessage());
        }
    }

    @Test
<<<<<<< HEAD
    public void testDeleteMessage() throws IOException, BadException, ClassNotFoundException {
        setInputData("LOGIN", "user", "password", "DELETE_MESSAGE", "recipient", "Hello!");
=======
    public void testDeleteMessage() {
        try {
            setInputData("LOGIN", "user", "password", "DELETE_MESSAGE", "recipient",
                    "Hello!");
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            User mockRecipient = new User("recipient",
                    "recipientpassword", true);

            mockUserDatabase.addUser(mockUser);

            mockUserDatabase.addUser(mockRecipient);

            mockUser.sendMessage(mockRecipient, "Hello!");

            clientHandler.run();

            assertEquals("Message deleted successfully.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testDeleteMessage: " + e.getMessage());
        }
    }

    @Test
    public void testSearchUser() {
        try {
            setInputData("SEARCH_USER", "user");

            mockUserDatabase.addUser(mockUser);

            clientHandler.run();

            assertEquals("User found: user", getOutputData());
        } catch (Exception e) {
            fail("Exception during testSearchUser: " + e.getMessage());
        }
    }

    @Test
    public void testViewUser() {
        try {
            setInputData("LOGIN", "user", "password", "VIEW_USER");

            mockUserDatabase.addUser(mockUser);

            clientHandler.run();

            assertEquals("Viewing your information: " + mockUser, getOutputData());
        } catch (Exception e) {
            fail("Exception during testViewUser: " + e.getMessage());
        }
    }

    @Test
    public void testLogout() {
        try {
            setInputData("LOGIN", "user", "password", "LOGOUT");

            clientHandler.run();

            assertEquals("You have logged out.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testLogout: " + e.getMessage());
        }
    }

    @Test
    public void testHandleInvalidAction() {
        try {
            setInputData("LOGIN", "user", "password", "INVALID_ACTION");

            clientHandler.run();

            assertEquals("Invalid action.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testInvalidActionHandling: " + e.getMessage());
        }
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        try {
            setInputData("LOGIN", "user", "wrongpassword");

            clientHandler.run();

            assertEquals("Error: Invalid username or password.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testLoginWithInvalidCredentials: " + e.getMessage());
        }
    }

    @Test
    public void testCreateAccountWithExistingUsername() {
        try {
            setInputData("CREATE_ACCOUNT", "user", "newpassword", "true");

            mockUserDatabase.addUser(mockUser);

            clientHandler.run();

            assertEquals("Error: Username already exists.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testCreateAccountWithExistingUsername: " + e.getMessage());
        }
    }

    @Test
    public void testAddFriendWhenNotLoggedIn() {
        try {
            setInputData("ADD_FRIEND", "friend");

            clientHandler.run();

            assertEquals("You must log in first.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testAddFriendWhenNotLoggedIn: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveFriendWhenNotLoggedIn() {
        try {
            setInputData("REMOVE_FRIEND", "friend");

            clientHandler.run();

            assertEquals("You must log in first.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testRemoveFriendWhenNotLoggedIn: " + e.getMessage());
        }
    }

    @Test
    public void testSendMessageWhenNotLoggedIn() {
        try {
            setInputData("SEND_MESSAGE", "recipient", "Hello!");

            clientHandler.run();

            assertEquals("You must log in first.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testSendMessageWhenNotLoggedIn: " + e.getMessage());
        }
    }

    @Test
    public void testLogoutWhenNotLoggedIn() {
        try {
            setInputData("LOGOUT");

            clientHandler.run();

            assertEquals("You have logged out.", getOutputData());
        } catch (Exception e) {
            fail("Exception during testLogoutWhenNotLoggedIn: " + e.getMessage());
        }
    }

<<<<<<< HEAD
    private void setInputData(String... inputs) throws IOException {
        StringBuilder inputData = new StringBuilder();
=======
    private void setInputData(String... inputs) {
        try {
            StringBuilder inputData = new StringBuilder();
>>>>>>> b5842c0d746462a46b889f1ac70343e6f589c055

            for (String input : inputs) {
                inputData.append(input).append("\n");
            }

            inputStream = new ByteArrayInputStream(inputData.toString().getBytes());

            mockIn = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            fail("Error setting input data: " + e.getMessage());
        }
    }

    private String getOutputData() {
        try {
            mockOut.flush();

            return outputStream.toString().trim();
        } catch (IOException e) {
            fail("Error retrieving output data: " + e.getMessage());

            return null;
        }
    }
} // End of class