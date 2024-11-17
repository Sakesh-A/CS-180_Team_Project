import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

public class ServerTest {

    private ServerSocket mockServerSocket;
    private Socket mockClientSocket;
    private ClientHandler mockClientHandler;
    private UserDatabase mockUserDatabase;
    private User mockUser;

    @BeforeEach
    public void setUp() throws IOException {
       
        mockServerSocket = mock(ServerSocket.class);
        mockClientSocket = mock(Socket.class);

        mockUserDatabase = mock(UserDatabase.class);
        mockUser = mock(User.class);
    }

    @Test
    public void testServerAcceptsClientConnection() throws IOException {
       
        when(mockServerSocket.accept()).thenReturn(mockClientSocket);

       
        Thread serverThread = new Thread(() -> {
            try {
                Server.main(new String[0]); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

      
        mockServerSocket.accept();

      
        verify(mockServerSocket, times(1)).accept();
    }

    @Test
    public void testAddLoggedInUser() {
      
        when(mockUser.getUsername()).thenReturn("user1");

        boolean added = Server.addLoggedInUser(mockUser);

        assertTrue(added);


        boolean addedAgain = Server.addLoggedInUser(mockUser);
        assertFalse(addedAgain);
    }

    @Test
    public void testRemoveLoggedInUser() {
       
        when(mockUser.getUsername()).thenReturn("user1");

        
        Server.addLoggedInUser(mockUser);

       
        Server.removeLoggedInUser(mockUser);

       
        assertFalse(Server.getLoggedInUsers().contains(mockUser));
    }

    @Test
    public void testConcurrencyInAddingLoggedInUsers() throws InterruptedException {
       
        Thread thread1 = new Thread(() -> {
            User user1 = new User("user1", "password1", true);
            Server.addLoggedInUser(user1);
        });

        Thread thread2 = new Thread(() -> {
            User user2 = new User("user1", "password2", true); 
            Server.addLoggedInUser(user2);
        });

        thread1.start();
        thread2.start();

     
        thread1.join();
        thread2.join();

        
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertEquals(1, loggedInUsers.size());
    }

    @Test
    public void testAddUserAlreadyLoggedIn() {
       
        when(mockUser.getUsername()).thenReturn("user1");

       
        boolean addedFirstTime = Server.addLoggedInUser(mockUser);
        assertTrue(addedFirstTime);

        
        boolean addedSecondTime = Server.addLoggedInUser(mockUser);
        assertFalse(addedSecondTime);
    }

    @Test
    public void testRemoveUserWhenNotLoggedIn() {
     
        Server.removeLoggedInUser(mockUser);

      
        assertDoesNotThrow(() -> Server.removeLoggedInUser(mockUser));
    }

    @Test
    public void testServerHandlesMultipleClientConnections() throws IOException {

        Socket clientSocket1 = mock(Socket.class);
        when(clientSocket1.getInetAddress()).thenReturn(InetAddress.getByName("127.0.0.1"));
        ClientHandler clientHandler1 = mock(ClientHandler.class);

      
        Socket clientSocket2 = mock(Socket.class);
        when(clientSocket2.getInetAddress()).thenReturn(InetAddress.getByName("127.0.0.2"));
        ClientHandler clientHandler2 = mock(ClientHandler.class);

        
        when(mockServerSocket.accept()).thenReturn(clientSocket1).thenReturn(clientSocket2);

       
        Thread serverThread = new Thread(() -> {
            try {
                Server.main(new String[0]); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

       
        mockServerSocket.accept();
        mockServerSocket.accept();

       
        verify(clientHandler1, times(1)).start();
        verify(clientHandler2, times(1)).start();
    }
}

