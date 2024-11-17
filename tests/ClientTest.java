import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientTest {

    private Socket mockSocket;
    private ObjectOutputStream mockOut;
    private ObjectInputStream mockIn;
    private Client client;

    @BeforeEach
    public void setUp() throws IOException {
       
        mockSocket = mock(Socket.class);
        mockOut = mock(ObjectOutputStream.class);
        mockIn = mock(ObjectInputStream.class);

       
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        
        client = new Client("localhost", 12345) {
            @Override
            protected void initializeStreams() throws IOException {
                
                out = mockOut;
                in = mockIn;
                socket = mockSocket;
            }
        };
    }

    @Test
    public void testClientConnectionSuccess() {
        
        assertDoesNotThrow(() -> new Client("localhost", 12345));
    }

    @Test
    public void testHandleCommunicationWhenServerSendsMessage() throws IOException, ClassNotFoundException {
    
        when(mockIn.readObject()).thenReturn("Welcome to the server!");

      
        client.handleCommunication();

       
        verify(mockOut, times(0)).writeObject(any()); // Verify no output is sent yet
    }

    @Test
    public void testHandleLogoutWhenUserSendsLogout() throws IOException, ClassNotFoundException {

        when(mockIn.readObject()).thenReturn("Welcome to the server!");

        ByteArrayInputStream inputStream = new ByteArrayInputStream("LOGOUT\n".getBytes());
        System.setIn(inputStream); 

        client.handleCommunication();

       
        verify(mockOut, times(1)).writeObject("LOGOUT");
    }

    @Test
    public void testServerDisconnects() throws IOException, ClassNotFoundException {
       
        when(mockIn.readObject()).thenReturn("You have logged out.");

        
        client.handleCommunication();

       
        verify(mockSocket, times(1)).close();
    }

    @Test
    public void testClientHandlesIOException() throws IOException, ClassNotFoundException {
       
        when(mockIn.readObject()).thenThrow(new IOException("Connection lost"));

        assertDoesNotThrow(() -> client.handleCommunication());

        verify(mockSocket, times(1)).close();
    }

    @Test
    public void testClientHandlesClassNotFoundException() throws IOException, ClassNotFoundException {

        when(mockIn.readObject()).thenThrow(new ClassNotFoundException("Unknown class"));

      
        assertDoesNotThrow(() -> client.handleCommunication());
    }

}

