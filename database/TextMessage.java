import java.util.*; 
import java.io.*;

/**
 * Team Project -- TextMessage
 *
 * Represents a text message object with basic properties of sender, receiver, and message content.
 * This class implements the MessageInterface and provides methods for accessing message details.
 * 
 * Authors: Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * Version: Nov 3, 2024
 */

public class TextMessage implements MessageInterface {
   
   // Stores sender, receiver usernames, and message content in a String array of length 3
   private String[] messageArray = new String[3];

   // Old code with direct attributes has been commented out to simplify access through the array
   // Uncomment if additional functionality or separate variables are needed in future
   
   /**
    * Constructor for TextMessage. Initializes the message array with sender username, receiver username, 
    * and message content.
    *
    * @param message the message content
    * @param sender the User object representing the message sender
    * @param receiver the User object representing the message receiver
    */
   public TextMessage(String message, User sender, User receiver) {
       messageArray[0] = sender.getUsername();
       messageArray[1] = receiver.getUsername();
       messageArray[2] = message;
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

   /**
    * Checks equality between this TextMessage instance and another object.
    * 
    * @param o the object to compare
    * @return true if o is a TextMessage with identical sender, receiver, and message; false otherwise
    */
   @Override
   public boolean equals(Object o) {
       if (!(o instanceof TextMessage m)) {
           return false;
       }

       return (this.messageArray[0].equals(m.messageArray[0]) &&
               this.messageArray[1].equals(m.messageArray[1]) &&
               this.messageArray[2].equals(m.messageArray[2]));
   }

   /**
    * Returns a String representation of the TextMessage.
    *
    * @return a formatted string containing sender, receiver, and message content
    */
   @Override
   public String toString() {
       return messageArray[0] + "," + messageArray[1] + "," + messageArray[2] + ";";
   }
}
