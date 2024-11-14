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
    private BufferedImage photo;
    private String photoURL;

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
        this.photo = imageIO.read(new file(photoURL));
    }

    /**
     * Gets the photo attachment associated with this message.
     *
     * @return the file path or URL of the photo as a String
     */
    public BufferedImage getPhoto() {
        return photo;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * Sets a new photo attachment for this message.
     *
     * @param photo the file path or URL of the new photo as a String
     */
    public void setPhoto(String photoURL) {
        this.photo = imageIO.read(new file(photoURL));
    }

    /**
    * Returns the username of the sender.
    *
    * @return sender's username as a String
    */
   public String getSenderUsername() {
       return messageArray[0];
   }

   /**
    * Returns the username of the receiver.
    *
    * @return receiver's username as a String
    */
   public String getReceiverUsername() {
       return messageArray[1];
   }

   /**
    * Returns the content of the message.
    *
    * @return message content as a String
    */
   public String getMessageContent() {
       return messageArray[2];
   }

   /**
    * Returns a copy of the messageArray to prevent direct modification of the original array.
    *
    * @return a cloned String array containing sender username, receiver username, and message content
    */
   public String[] getMessageArray() {
       return messageArray.clone();
   }

   public User getSender() {
        return sender;
    }



    public User getReceiver() {
        return receiver;
    }
}
