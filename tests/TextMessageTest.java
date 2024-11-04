import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
}
