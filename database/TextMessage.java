import java.util.*;
import java.io.*;

/**
 * Team Project -- TextMessage
 *
 * Creates a TextMessage object and includes all needed methods including gets and sets
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public class TextMessage implements MessageInterface{
   private String[] messageArray = new String[3];

//    private String message;
//    private User sender;
//    private User receiver;
    private int messageId;
//    public static int id = 0;

    public TextMessage(String message, User sender, User receiver) {
        messageArray[0] = sender.getUsername();
        messageArray[1] = receiver.getUsername();
        messageArray[2] = message;

        //        this.message = message;
//        this.sender = sender;
//        this.receiver = receiver;
//        messageId = id;
    }
//    public int getMessageId() {
//        return messageId;
//    }

//   public  String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public User getSender() {
//        return sender;
//    }
//
//    public User getReceiver() {
//        return receiver;
//    }
//
//    public void setSender(User sender) {
//        this.sender = sender;
//    }
//
//    public void setReceiver(User receiver) {
//        this.receiver = receiver;
//    }
    public String[] getMessageArray() {
        return messageArray;
    }


    public boolean equals(Object o) {
        if(!(o instanceof User)) {
            return false;
        }
        TextMessage m = (TextMessage) o;

        return this.message.equals(m.message);
//        return m.messageId == this.messageId;
    }

}