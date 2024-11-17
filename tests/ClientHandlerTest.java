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
       
        mockSocket = mock(Socket.class);
        mockOut = mock(ObjectOutputStream.class);
        mockIn = mock(ObjectInputStream.class);
        mockUserDatabase = mock(UserDatabase.class);
        mockUser = mock(User.class);

        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        
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

    
        when(mockUserDatabase.addUser(any(User.class))).thenReturn(true);
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockUser));
    }

    @Test
    public void testClientLogin() throws IOException, ClassNotFoundException {
       
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        clientHandler.run();

        
        verify(mockOut, times(1)).writeObject("Login successful.");
    }

    @Test
    public void testCreateAccount() throws IOException, ClassNotFoundException {
      
        when(mockIn.readObject()).thenReturn("CREATE_ACCOUNT");
        when(mockIn.readObject()).thenReturn("newuser");
        when(mockIn.readObject()).thenReturn("newpassword");
        when(mockIn.readObject()).thenReturn("true");

       
        when(mockUserDatabase.addUser(any(User.class))).thenReturn(true);

        clientHandler.run();

        // Verify that account creation was successful
        verify(mockOut, times(1)).writeObject("Account created successfully.");
    }

    @Test
    public void testAddFriend() throws IOException, ClassNotFoundException {
        
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

        
        when(mockIn.readObject()).thenReturn("ADD_FRIEND");
        when(mockIn.readObject()).thenReturn("friend");

      
        User mockFriend = mock(User.class);
        when(mockFriend.getUsername()).thenReturn("friend");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockFriend));

        when(mockUser.addFriend(mockFriend)).thenReturn(true);

        clientHandler.run();

        
        verify(mockOut, times(1)).writeObject("Friend added successfully.");
    }

    @Test
    public void testRemoveFriend() throws IOException, ClassNotFoundException {

        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

       
        when(mockIn.readObject()).thenReturn("REMOVE_FRIEND");
        when(mockIn.readObject()).thenReturn("friend");

        
        User mockFriend = mock(User.class);
        when(mockFriend.getUsername()).thenReturn("friend");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockFriend));

      
        when(mockUser.removeFriend(mockFriend)).thenReturn(true);

        clientHandler.run();

      
        verify(mockOut, times(1)).writeObject("Friend removed successfully.");
    }

    @Test
    public void testSendMessage() throws IOException, ClassNotFoundException {
     
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

     
        when(mockIn.readObject()).thenReturn("SEND_MESSAGE");
        when(mockIn.readObject()).thenReturn("recipient");
        when(mockIn.readObject()).thenReturn("Hello!");

     
        User mockRecipient = mock(User.class);
        when(mockRecipient.getUsername()).thenReturn("recipient");
        when(mockUserDatabase.getUsers()).thenReturn(java.util.Collections.singletonList(mockRecipient));

        when(mockUser.sendMessage(mockRecipient, "Hello!")).thenReturn(true);

        clientHandler.run();

       
        verify(mockOut, times(1)).writeObject("Message sent successfully.");
    }

    @Test
    public void testLogout() throws IOException {

        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

   
        when(mockIn.readObject()).thenReturn("LOGOUT");

     
        clientHandler.run();

    
        verify(mockOut, times(1)).writeObject("You have logged out.");
    }

    @Test
    public void testHandleInvalidAction() throws IOException, ClassNotFoundException {
     
        when(mockIn.readObject()).thenReturn("LOGIN");
        when(mockIn.readObject()).thenReturn("user");
        when(mockIn.readObject()).thenReturn("password");

    
        when(mockIn.readObject()).thenReturn("INVALID_ACTION");

        clientHandler.run();

        
        verify(mockOut, times(1)).writeObject("Invalid action.");
    }
}
