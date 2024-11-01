import java.util.*;
import java.io.*;

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

    public String getMessageSent() {
        return message;
    }

    public String getMessageReceived() {
        return message;
    }

    public void setMessageSent(String messageSent) {
        this.message = messageSent;
    }

    public void setMessageReceived(String messageReceived) {
        this.message = messageReceived;
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

    public void setUser(User sender){
        this.sender = sender;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setReceivers(User[] receivers){
        this.receivers = receivers;
    }

}
