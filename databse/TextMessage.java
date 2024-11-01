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

    private String message;
    private User sender;
    private User receiver;
    private User[] receivers;

    public TextMessage(String message, User sender, User receiver, User[] receivers) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.receivers = receivers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver(){
        return receiver;
    }

    public User[] getReceivers(){
        return receivers;
    }

    public void setSender(User sender){
        this.sender = sender;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setReceivers(User[] receivers){
        this.receivers = receivers;
    }

}
