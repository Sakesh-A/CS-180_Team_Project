import java.util.*;
import java.io.*;

/**
 * Team Project -- MessageInterface
 *
 * Creates an interface for Message
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public interface MessageInterface {
    String[] getMessageArray();
    boolean equals(Object o);
    String toString();
    String getSenderUsername();
    String getReceiverUsername();
    String getMessageContent();
//    public static int id = 0; //Everytime you send a message, id++, gives each message a unique id for identification purposes
//    public String getMessage();
//    public User getReceiver();
//    public User getSender();
//    public void setMessage(String message);
//    public void setReceiver(User receiver);
//    public void setSender(User sender);

}