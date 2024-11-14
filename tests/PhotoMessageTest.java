import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Team Project -- PhotoMessageTest
 *
 * Unit tests for the PhotoMessage class, verifying the proper functionality of
 * message content, sender and receiver usernames, and photo path management.
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */
public class PhotoMessageTest {

    private User sender;
    private User receiver;
    private PhotoMessage photoMessage;

    @Before
    public void setUp() throws BadException {
        sender = new User("Alice", "Password1!", true);
        receiver = new User("Bob", "Password2@", true);
        photoMessage = new PhotoMessage("Check out this photo!", sender, receiver, "path/to/photo.jpg");
    }

    @Test
    public void testGetPhoto() {
        assertEquals("Photo path should be 'path/to/photo.jpg'", "path/to/photo.jpg", photoMessage.getPhoto());
    }

    @Test
    public void testSetPhoto() {
        photoMessage.setPhoto("new/path/to/photo.jpg");
        assertEquals("Photo path should be updated to 'new/path/to/photo.jpg'",
                     "new/path/to/photo.jpg", photoMessage.getPhoto());
    }

    @Test
    public void testGetSenderUsername() {
        assertEquals("Sender username should be 'Alice'", "Alice", photoMessage.getSenderUsername());
    }

    @Test
    public void testGetReceiverUsername() {
        assertEquals("Receiver username should be 'Bob'", "Bob", photoMessage.getReceiverUsername());
    }

    @Test
    public void testGetMessageContent() {
        assertEquals("Message content should be 'Check out this photo!'", 
                     "Check out this photo!", photoMessage.getMessageContent());
    }

    @Test
    public void testGetMessageArray() {
        String[] expectedArray = {"Alice", "Bob", "Check out this photo!"};
        assertArrayEquals("Message array should contain sender, receiver, and message content", 
                          expectedArray, photoMessage.getMessageArray());
    }

    @Test
    public void testEquals() {
        PhotoMessage anotherPhotoMessage = new PhotoMessage("Check out this photo!", 
                                                            sender, receiver, "path/to/photo.jpg");
        assertTrue("Photo messages should be equal", photoMessage.equals(anotherPhotoMessage));

        PhotoMessage differentPhotoMessage = new PhotoMessage("Different message", 
            sender, receiver, "path/to/photo.jpg");
        assertFalse("Photo messages should not be equal", photoMessage.equals(differentPhotoMessage));

        assertFalse("Photo message should not equal null", photoMessage.equals(null));
        assertFalse("Photo message should not equal different object", photoMessage.equals("Not a PhotoMessage"));
    }

    @Test
    public void testToString() {
        String expectedString = "Alice,Bob,Check out this photo!;";
        assertEquals("toString should return formatted string", expectedString, photoMessage.toString());
    }
}
