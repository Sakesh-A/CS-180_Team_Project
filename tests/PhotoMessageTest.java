import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Team Project -- PhotoMessageTest
 *
 * Tests for the PhotoMessage class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class PhotoMessageTest {

    private User sender;
    private User receiver;
    private String messageContent;
    private String photoURL;
    private PhotoMessage photoMessage;

    @Before
    public void setUp() throws IOException {

        sender = new User("senderUser", "password", true);
        receiver = new User("receiverUser", "password", false);
        messageContent = "Hello, this is a photo message!";
        photoURL = "src/test/resources/test-photo.jpg";


        photoMessage = new PhotoMessage(messageContent, sender, receiver, photoURL);
    }

    @Test
    public void testConstructor() {

        assertNotNull("PhotoMessage should be instantiated", photoMessage);
    }

    @Test
    public void testGetPhoto() throws IOException {

        BufferedImage photo = photoMessage.getPhoto();
        assertNotNull("Photo should not be null", photo);
        assertTrue("Photo should be an instance of BufferedImage", photo instanceof BufferedImage);
    }

    @Test
    public void testGetPhotoURL() {

        assertEquals("Photo URL should match the provided URL", photoURL, photoMessage.getPhotoURL());
    }

    @Test
    public void testGetSenderUsername() {

        assertEquals("Sender's username should be correct", sender.getUsername(), photoMessage.getSenderUsername());
    }

    @Test
    public void testGetReceiverUsername() {

        assertEquals("Receiver's username should be correct", receiver.getUsername(), photoMessage.getReceiverUsername());
    }

    @Test
    public void testGetMessageContent() {

        assertEquals("Message content should match", messageContent, photoMessage.getMessageContent());
    }

    @Test
    public void testGetMessageArray() {

        String[] messageArray = photoMessage.getMessageArray();
        assertNotNull("Message array should not be null", messageArray);
        assertEquals("Message array should contain correct sender username", sender.getUsername(), messageArray[0]);
        assertEquals("Message array should contain correct receiver username", receiver.getUsername(), messageArray[1]);
        assertEquals("Message array should contain correct message content", messageContent, messageArray[2]);
        assertEquals("Message array should contain correct photo URL", photoURL, messageArray[3]);


        messageArray[0] = "NewUsername";
        assertNotEquals("Sender username in original message array should not change", "NewUsername", photoMessage.getMessageArray()[0]);
    }

    @Test
    public void testToString() {

        String expectedString = sender.getUsername() + "," + receiver.getUsername() + "," + messageContent + "," + photoURL + ";";
        assertEquals("toString should return correct string format", expectedString, photoMessage.toString());
    }

    @Test(expected = IOException.class)
    public void testConstructorWithInvalidPhotoURL() throws IOException {

        String invalidPhotoURL = "invalid/photo/path.jpg";
        new PhotoMessage(messageContent, sender, receiver, invalidPhotoURL);
    }

    @Test
    public void testCloneMessageArray() {

        String[] originalMessageArray = photoMessage.getMessageArray();
        originalMessageArray[0] = "NewSender";
        assertNotEquals("Original message array should not be affected by modification", "NewSender", photoMessage.getMessageArray()[0]);
    }
}
