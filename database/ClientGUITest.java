import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.net.Socket;


public class ClientGUITest {

    private Socket mockSocket;
    private ObjectInputStream mockInputStream;
    private ObjectOutputStream mockOutputStream;
    private ClientGUI clientGUI;

    @Before
    public void setUp() throws IOException {
        // Mocking the Socket and streams
        mockSocket = new Socket("localhost", 4242);
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(new byte[0]));  // Mock input stream
        mockOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());         // Mock output stream

        // Injecting the mock socket and streams into the ClientGUI
        clientGUI = new ClientGUI(mockSocket);
        clientGUI.setIn(mockInputStream);
        clientGUI.setOut(mockOutputStream);
    }

    @Test
    public void testAddFriend() throws IOException, ClassNotFoundException {
        // Simulate adding a friend
        String friendUsername = "testFriend";
        String expectedResponse = "Friend added successfully";

        // Simulate sending the "ADD_FRIEND" action to the server
        mockOutputStream.writeObject("ADD_FRIEND");
        mockOutputStream.writeObject(friendUsername);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers the action
        clientGUI.createAddFriendPanel();

        // Verify the request and response
        assertTrue(mockOutputStream.toString().contains("ADD_FRIEND"));
        assertTrue(mockOutputStream.toString().contains(friendUsername));
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }

    @Test
    public void testSendMessage() throws IOException, ClassNotFoundException {
        // Simulate sending a message
        String recipientUsername = "testUser";
        String message = "Hello!";
        String expectedResponse = "Message sent successfully";

        // Simulate sending the "SEND_MESSAGE" action
        mockOutputStream.writeObject("SEND_MESSAGE");
        mockOutputStream.writeObject(recipientUsername);
        mockOutputStream.writeObject(message);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers sending the message
        clientGUI.createSendMessagePanel();

        // Verify the request and response
        assertTrue(mockOutputStream.toString().contains("SEND_MESSAGE"));
        assertTrue(mockOutputStream.toString().contains(recipientUsername));
        assertTrue(mockOutputStream.toString().contains(message));
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }





    @Test
    public void testRemoveFriend() throws IOException, ClassNotFoundException {
        // Simulate removing a friend
        String friendUsername = "testFriend";
        String expectedResponse = "Friend removed successfully";

        // Simulate sending the "REMOVE_FRIEND" action to the server
        mockOutputStream.writeObject("REMOVE_FRIEND");
        mockOutputStream.writeObject(friendUsername);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers the action
        clientGUI.createRemoveFriendPanel();

        // Verify the request and response
        assertTrue(mockOutputStream.toString().contains("REMOVE_FRIEND"));
        assertTrue(mockOutputStream.toString().contains(friendUsername));
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }

    @Test
    public void testBackButtonNavigation() {
        // Simulate that the "ActionMenu" is the panel currently being displayed
        clientGUI.getCardLayout().show(clientGUI.getMainPanel(), "ActionMenu");

        // Verify that the ActionMenu panel is displayed
        Component currentPanel = clientGUI.getMainPanel().getComponent(0);
        assertEquals("ActionMenu", currentPanel.getName());

        // Simulate the back button being clicked
        clientGUI.getCardLayout().show(clientGUI.getMainPanel(), "MainMenu");

        // Verify that the MainMenu panel is displayed after clicking the back button
        Component panelAfterBack = clientGUI.getMainPanel().getComponent(0);
        assertEquals("MainMenu", panelAfterBack.getName());
    }

    @Test
    public void testSearchUserWithEmptyInput() throws IOException, ClassNotFoundException {
        // Simulate empty search input (empty username)
        String searchUsername = "";
        String expectedResponse = "Please enter a username to search.";

        // Simulate sending the "SEARCH_USER" action to the server
        mockOutputStream.writeObject("SEARCH_USER");
        mockOutputStream.writeObject(searchUsername);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the search functionality
        clientGUI.createSearchUserPanel();

        // Verify the correct error response is handled
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }

    @Test
    public void testViewUserProfileWhenNoInfoAvailable() throws IOException, ClassNotFoundException {
        // Simulate the scenario where no profile information is available
        String expectedResponse = "No profile information available.";

        // Simulate sending the "VIEW_USER" action
        mockOutputStream.writeObject("VIEW_USER");

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers the view profile action
        clientGUI.createViewUserPanel();

        // Verify that the response was handled correctly
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }

    @Test
    public void testInvalidUsernameAddFriend() throws IOException, ClassNotFoundException {
        // Simulate adding a friend with an invalid username (empty string)
        String friendUsername = "";
        String expectedResponse = "Please enter a friend's username.";

        // Simulate sending the "ADD_FRIEND" action
        mockOutputStream.writeObject("ADD_FRIEND");
        mockOutputStream.writeObject(friendUsername);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers the action
        clientGUI.createAddFriendPanel();

        // Verify that an error message is displayed for invalid input
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }

    @Test
    public void testBlockSelf() throws IOException {
        // Simulate blocking oneself
        String usernameToBlock = "currentUser"; // The same as the logged-in user
        String expectedResponse = "You cannot block yourself.";

        // Simulate the block user action
        mockOutputStream.writeObject("BLOCK_USER");
        mockOutputStream.writeObject(usernameToBlock);

        // Simulate server response
        mockInputStream = new ObjectInputStream(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Call the method that triggers the action
        clientGUI.createBlockUserPanel();

        // Verify that the error message is displayed
        assertTrue(expectedResponse.equals(new String(mockInputStream.readAllBytes())));
    }
}
