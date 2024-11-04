import java.util.*; 
import java.io.*; 

/**
 * Team Project -- MessageInterface
 *
 * Defines an interface for a message, specifying required methods for message handling. 
 * This includes methods for retrieving message details like sender, receiver, and content.
 * 
 * Authors: Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * Version: Nov 3, 2024
 */

public interface MessageInterface {

    /**
     * Returns an array representation of the message, typically including sender, receiver, 
     * and message content.
     * 
     * @return a String array containing message details
     */
    String[] getMessageArray();

    /**
     * Compares this message object with another object for equality based on message content.
     * 
     * @param o the object to compare
     * @return true if the messages are equivalent; false otherwise
     */
    boolean equals(Object o);

    /**
     * Returns a String representation of the message, typically formatted with sender, 
     * receiver, and content details.
     * 
     * @return a formatted string representation of the message
     */
    String toString();

    /**
     * Retrieves the sender's username.
     * 
     * @return sender's username as a String
     */
    String getSenderUsername();

    /**
     * Retrieves the receiver's username.
     * 
     * @return receiver's username as a String
     */
    String getReceiverUsername();

    /**
     * Retrieves the message content.
     * 
     * @return message content as a String
     */
    String getMessageContent();
}
