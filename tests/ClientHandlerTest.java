import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientHandlerTest {

    private ClientHandler clientHandler;
    private Socket mockSocket;
    private ObjectOutputStream mockOut;
    private ObjectInputStream mockIn;
    private UserDatabase mockUserDatabase;
    private User mockUser;

    @BeforeEach
    public void setUp() throws IOException {
        // Mock dependencies
        mockSocket = mock(Socket.class);
        mockOut = mock(ObjectOutputStream.class);
        mockIn = mock(ObjectInputStream.class);
        mockUserDatabase = mock(UserDatabase.class);
        mockUser = mock(User.class);

        // Mock the socket streams
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        // Initialize ClientHandler with mocks
        clientHandler = new ClientHandler(mockSocket, mockUserDatabase) {
            @Override
            protected void authenticateUser() throws IOException, ClassNotFoundException {
                // Mock the user authentication flow
                when(mockIn.readObject()).thenReturn("LOGIN");
                when(mockIn.readObject()).thenReturn("user");
                when(mockIn.readObject()).thenReturn("password");
                when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockUser));
                when(mockUser.getUsername()).thenReturn("user");
                when(mockUser.getPassword()).thenReturn("password");
                when(mockUser.getMessages()).thenReturn(java.util.Collections.emptyList());
            }
        };

        // Mock the behavior for the login
        when(mockUserDatabase.addUser(any(User.class))).thenReturn(true);
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockUser));
    }

    @Test
    public void testClientLogin() throws IOException, ClassNotFoundException {
        // Simulate the login process
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        clientHandler.run();

        // Verify the login process
        verify(mockOut, times(1)).writeObject("Login successful.");
    }

    @Test
    public void testCreateAccount() throws IOException, ClassNotFoundException {
        // Simulate creating a new account
        when(mockIn.readObject()).thenReturn("CREATE_ACCOUNT");
        when(mockIn.readObject()).thenReturn("newuser");
        when(mockIn.readObject()).thenReturn("newpassword");
        when(mockIn.readObject()).thenReturn("true");

        // Mock that the account is successfully created
        when(mockUserDatabase.addUser(any(User.class))).thenReturn(true);

        clientHandler.run();

        // Verify that account creation was successful
        verify(mockOut, times(1)).writeObject("Account created successfully.");
    }

    @Test
    public void testAddFriend() throws IOException, ClassNotFoundException {
        // Simulate login
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        // Simulate the Add Friend action
        when(mockIn.readObject()).thenReturn("ADD_FRIEND");
        when(mockIn.readObject()).thenReturn("friend");

        // Mock behavior for adding a friend
        User mockFriend = mock(User.class);
        when(mockFriend.getUsername()).thenReturn("friend");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockFriend));

        // Simulate that the user is successfully added as a friend
        when(mockUser.addFriend(mockFriend)).thenReturn(true);

        clientHandler.run();

        // Verify the add friend response
        verify(mockOut, times(1)).writeObject("Friend added successfully.");
    }

    @Test
    public void testRemoveFriend() throws IOException, ClassNotFoundException {
        // Simulate login
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        // Simulate the Remove Friend action
        when(mockIn.readObject()).thenReturn("REMOVE_FRIEND");
        when(mockIn.readObject()).thenReturn("friend");

        // Mock behavior for removing a friend
        User mockFriend = mock(User.class);
        when(mockFriend.getUsername()).thenReturn("friend");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockFriend));

        // Simulate that the user is successfully removed from the friend list
        when(mockUser.removeFriend(mockFriend)).thenReturn(true);

        clientHandler.run();

        // Verify the remove friend response
        verify(mockOut, times(1)).writeObject("Friend removed successfully.");
    }

    @Test
    public void testSendMessage() throws IOException, ClassNotFoundException {
        // Simulate login
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        // Simulate sending a message
        when(mockIn.readObject()).thenReturn("SEND_MESSAGE");
        when(mockIn.readObject()).thenReturn("recipient");
        when(mockIn.readObject()).thenReturn("Hello!");

        // Mock recipient user
        User mockRecipient = mock(User.class);
        when(mockRecipient.getUsername()).thenReturn("recipient");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockRecipient));

        // Simulate successful message sending
        when(mockUser.sendMessage(mockRecipient, "Hello!")).thenReturn(true);

        clientHandler.run();

        // Verify the send message response
        verify(mockOut, times(1)).writeObject("Message sent successfully.");
    }

    @Test
    public void testLogout() throws IOException {
        // Simulate login
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        // Simulate logout
        when(mockIn.readObject()).thenReturn("LOGOUT");

        // Run the client handler
        clientHandler.run();

        // Verify that the logout message was sent
        verify(mockOut, times(1)).writeObject("You have logged out.");
    }

    @Test
    public void testHandleInvalidAction() throws IOException, ClassNotFoundException {
        // Simulate login
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        // Simulate invalid action
        when(mockIn.readObject()).thenReturn("INVALID_ACTION");

        clientHandler.run();

        // Verify the invalid action message
        verify(mockOut, times(1)).writeObject("Invalid action.");
    }
}
