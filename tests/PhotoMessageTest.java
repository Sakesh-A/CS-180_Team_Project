import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * Team Project -- PhotoMessageTest
 *
 * Unit tests for the PhotoMessage class, verifying the proper functionality of
 * message content, sender and receiver usernames, and photo path management.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */
public class PhotoMessageTest {

    private User sender;
    private User receiver;
    private PhotoMessage photoMessage;

    @Before
    public void setUp() throws BadException, IOException {
        sender = new User("Alice", "Password1!", true);
        receiver = new User("Bob", "Password2@", true);
        photoMessage = new PhotoMessage("Check out this photo!", sender, receiver, "path/to/photo.jpg");
    }

    @Test
    public void testGetPhoto() {
        assertEquals("Photo path should be 'path/to/photo.jpg'", "path/to/photo.jpg", photoMessage.getPhotoURL());
    }

    @Test
    public void testSetPhoto() throws IOException {
        photoMessage.setPhoto("new/path/to/photo.jpg");
        assertEquals("Photo path should be updated to 'new/path/to/photo.jpg'",
                     "new/path/to/photo.jpg", photoMessage.getPhotoURL());
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

    
    @Test
    public void testInvalidPhotoURL() {
        try {
            PhotoMessage invalidPhotoMessage = new PhotoMessage("Invalid photo message", sender, receiver, "invalid/path/photo.jpg");
            fail("Expected IOException due to invalid photo URL");
        } catch (IOException e) {
            // expected
        }
    }

    
    @Test
    public void testNullPhotoURL() {
        try {
            PhotoMessage invalidPhotoMessage = new PhotoMessage("Null photo URL", sender, receiver, null);
            fail("Expected IOException due to null photo URL");
        } catch (IOException e) {
            // expected
        }
    }

    
    @Test
    public void testNonImageFile() {
        try {
           
            File nonImageFile = new File("test.txt");
            nonImageFile.createNewFile();
            
           
            PhotoMessage nonImageMessage = new PhotoMessage("Message with non-image file", sender, receiver, "test.txt");
            fail("Expected IOException for non-image file");
        } catch (IOException e) {
           //expected
        }
    }

    


    
    @Test
    public void testEmptyMessageWithPhoto() {
        try {
            PhotoMessage emptyMessageWithPhoto = new PhotoMessage("", sender, receiver, "path/to/photo.jpg");
            assertEquals("Message content should be empty", "", emptyMessageWithPhoto.getMessageContent());
        } catch (IOException e) {
            fail("IOException should not be thrown for empty message: " + e.getMessage());
        }
    }

   
    @Test
    public void testEmptyPhotoURL() {
        try {
            PhotoMessage emptyPhotoURLMessage = new PhotoMessage("Photo with empty URL", sender, receiver, "");
            assertEquals("Photo URL should be empty", "", emptyPhotoURLMessage.getPhotoURL());
        } catch (IOException e) {
            fail("IOException should not be thrown for empty photo URL: " + e.getMessage());
        }
    }

    @Test
    public void testSameSenderAndReceiver() {
        User sameUser = new User("Alice", "Password1!", true);
        try {
            PhotoMessage messageToSelf = new PhotoMessage("Message to self with photo", sameUser, sameUser, "path/to/photo.jpg");
            assertEquals("Sender and receiver should be the same username", "Alice", messageToSelf.getSenderUsername());
            assertEquals("Sender and receiver should be the same username", "Alice", messageToSelf.getReceiverUsername());
            assertEquals("Message content should be 'Message to self with photo'", "Message to self with photo", messageToSelf.getMessageContent());
        } catch (IOException e) {
            fail("IOException should not be thrown for message to self: " + e.getMessage());
        }
    }

 
    @Test
    public void testInvalidPhotoFormat() {
        try {
            
            File invalidImageFile = new File("unsupported_format.gif");
            invalidImageFile.createNewFile();

            PhotoMessage invalidFormatMessage = new PhotoMessage("Message with invalid format photo", sender, receiver, "unsupported_format.gif");
            fail("Expected IOException for unsupported image format");
        } catch (IOException e) {
            // expected
        }
    }


    
    @Test
    public void testPhotoMessageWithoutPhoto() {
        try {
            
            PhotoMessage messageWithoutPhoto = new PhotoMessage("Just a text message", sender, receiver, "");
            assertEquals("Message content should be 'Just a text message'", "Just a text message", messageWithoutPhoto.getMessageContent());
            assertEquals("Photo URL should be empty", "", messageWithoutPhoto.getPhotoURL());
        } catch (IOException e) {
            fail("IOException should not be thrown for photo message without a photo: " + e.getMessage());
        }
    }
}

