import java.util.*;
import java.io.*;

/**
 * Team Project -- PhotoMessage
 *
 * Represents a photo message with additional attributes for sender, receiver, message content, 
 * and a photo attachment. Extends the TextMessage class to include photo-related functionality.
 * 
* @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */

public class PhotoMessage extends TextMessage {

    // Stores the file path or URL to the photo attached to the message
    private String photo;

    /**
     * Constructs a PhotoMessage object with message content, sender, receiver, and a photo.
     *
     * @param message the text content of the message
     * @param sender the User object representing the sender
     * @param receiver the User object representing the receiver
     * @param photo the file path or URL to the photo attachment as a String
     */
    public PhotoMessage(String message, User sender, User receiver, String photo) {
        super(message, sender, receiver);
        this.photo = photo;
    }

    /**
     * Gets the photo attachment associated with this message.
     *
     * @return the file path or URL of the photo as a String
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets a new photo attachment for this message.
     *
     * @param photo the file path or URL of the new photo as a String
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
