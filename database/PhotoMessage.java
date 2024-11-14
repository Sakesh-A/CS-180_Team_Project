import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


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
    private String photURL;

    /**
     * Constructs a PhotoMessage object with message content, sender, receiver, and a photo.
     *
     * @param message the text content of the message
     * @param sender the User object representing the sender
     * @param receiver the User object representing the receiver
     * @param photo the file path or URL to the photo attachment as a String
     */
    public PhotoMessage(String message, User sender, User receiver, String photoURL) {
        super(message, sender, receiver);
        this.photoURL = photoURL;
    }

    /**
     * Gets the photo attachment associated with this message.
     *
     * @return the file path or URL of the photo as a String
     */
    public String getPhotoURL() {
        return photoURL;
    }

    /**
     * Sets a new photo attachment for this message.
     *
     * @param photo the file path or URL of the new photo as a String
     */
    public void setPhoto(String photoURL) {
        this.photo = photoURL;
    }
}
