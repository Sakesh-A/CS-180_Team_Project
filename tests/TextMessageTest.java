import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Team Project -- TextMessageTest
 *
 * Unit tests for the TextMessage class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */
public class TextMessageTest {

    private User sender;
    private User receiver;
    private TextMessage textMessage;

    @Before
    public void setUp() throws BadException {
        sender = new User("Alice", "Password1!", true);
        receiver = new User("Bob", "Password2@", true);
        textMessage = new TextMessage("Hello, Bob!", sender, receiver);
    }

    @Test
    public void testConstructorWithEmptyMessage() {
        TextMessage message = new TextMessage("", sender, receiver);
        assertEquals("Sender username should match", "Alice", message.getSenderUsername());
        assertEquals("Receiver username should match", "Bob", message.getReceiverUsername());
        assertEquals("Message content should be empty", "", message.getMessageContent());
    }

    @Test
    public void testGetSenderUsername() {
        assertEquals("Sender username should be Alice", "Alice", textMessage.getSenderUsername());
    }

    @Test
    public void testGetReceiverUsername() {
        assertEquals("Receiver username should be Bob", "Bob", textMessage.getReceiverUsername());
    }

    @Test
    public void testGetMessageContent() {
        assertEquals("Message content should be 'Hello, Bob!'", "Hello, Bob!", textMessage.getMessageContent());
    }

    @Test
    public void testGetMessageArray() {
        String[] expectedArray = {"Alice", "Bob", "Hello, Bob!"};
        assertArrayEquals("Message array should contain sender, receiver, and message content", expectedArray, textMessage.getMessageArray());
    }

    @Test
    public void testEquals() {
        TextMessage anotherMessage = new TextMessage("Hello, Bob!", sender, receiver);
        assertTrue("Messages should be equal", textMessage.equals(anotherMessage));

        TextMessage differentMessage = new TextMessage("Goodbye, Bob!", sender, receiver);
        assertFalse("Messages should not be equal", textMessage.equals(differentMessage));

        assertFalse("Message should not equal null", textMessage.equals(null));
        assertFalse("Message should not equal different object", textMessage.equals("Not a TextMessage"));
    }

    @Test
    public void testToString() {
        String expectedString = "Alice,Bob,Hello, Bob!;";
        assertEquals("toString should return formatted string", expectedString, textMessage.toString());
    }

    @Test
    public void testLargeMessageContent() {
        String largeMessage = "A".repeat(1000);  // Message with 1000 characters
        TextMessage largeMessageText = new TextMessage(largeMessage, sender, receiver);
        assertEquals("Message content should be equal to the large message", largeMessage, largeMessageText.getMessageContent());
    }

   
    @Test
    public void testMessageWithSpecialCharacters() {
        String specialMessage = "@#$%^&*()_+[]{}|;:,.<>?/~`";
        TextMessage specialMessageText = new TextMessage(specialMessage, sender, receiver);
        assertEquals("Message content should be the same as special characters message", specialMessage, specialMessageText.getMessageContent());
    }

   
    @Test
    public void testMessageWithWhitespace() {
        String whitespaceMessage = "    ";  // Message with only spaces
        TextMessage whitespaceMessageText = new TextMessage(whitespaceMessage, sender, receiver);
        assertEquals("Message content should be whitespace", whitespaceMessage, whitespaceMessageText.getMessageContent());
    }

    @Test
    public void testMessageWithNullCharacter() {
        String messageWithNull = "Hello\0Bob!";
        TextMessage messageText = new TextMessage(messageWithNull, sender, receiver);
        assertEquals("Message content should correctly handle null character", messageWithNull, messageText.getMessageContent());
    }


    @Test
    public void testEqualityWithNullUser() {
        TextMessage nullSenderMessage = new TextMessage("Hello, Bob!", null, receiver);
        TextMessage anotherMessage = new TextMessage("Hello, Bob!", sender, receiver);
        assertFalse("Messages with null sender should not be equal", textMessage.equals(nullSenderMessage));
        assertFalse("Messages with null receiver should not be equal", textMessage.equals(anotherMessage));
    }

    @Test
    public void testDifferentReceiver() {
        User receiver2 = new User("Charlie", "Password3#", true);
        TextMessage messageToCharlie = new TextMessage("Hello, Charlie!", sender, receiver2);
        assertFalse("Messages with different receivers should not be equal", textMessage.equals(messageToCharlie));
    }

    @Test
    public void testSenderAndReceiverSameUsername() {
        User sameUser = new User("Alice", "Password1!", true);
        TextMessage messageToSelf = new TextMessage("Hello, Alice!", sameUser, sameUser);
        assertEquals("Sender and receiver should be the same username", "Alice", messageToSelf.getSenderUsername());
        assertEquals("Sender and receiver should be the same username", "Alice", messageToSelf.getReceiverUsername());
        assertEquals("Message content should be 'Hello, Alice!'", "Hello, Alice!", messageToSelf.getMessageContent());
    }


    @Test
    public void testMessageArrayImmutability() {
        String[] clonedArray = textMessage.getMessageArray();
        clonedArray[0] = "Altered"; // Modify the cloned array
        assertNotEquals("Original array should not be affected", "Altered", textMessage.getMessageArray()[0]);
    }

    @Test
    public void testMessageWithEmptyReceiverUsername() {
        User emptyReceiver = new User("", "Password1@", true);
        try {
            TextMessage message = new TextMessage("Hello, there!", sender, emptyReceiver);
            assertEquals("Receiver username should be empty", "", message.getReceiverUsername());
        } catch (Exception e) {
            fail("Should not throw exception for empty receiver username");
        }
    }

    @Test
    public void testMessageWithEmptySenderUsername() {
        User emptySender = new User("", "Password2@", true);
        try {
            TextMessage message = new TextMessage("Hello, there!", emptySender, receiver);
            assertEquals("Sender username should be empty", "", message.getSenderUsername());
        } catch (Exception e) {
            fail("Should not throw exception for empty sender username");
        }
    }

    @Test
    public void testLongUsernames() {
        String longUsername = "A".repeat(100);  // Username with 100 characters
        User longSender = new User(longUsername, "Password1!", true);
        User longReceiver = new User("Bob", "Password2@", true);
        TextMessage longUsernameMessage = new TextMessage("Message to long username", longSender, longReceiver);
        assertEquals("Sender username should be the long username", longUsername, longUsernameMessage.getSenderUsername());
    }
}
