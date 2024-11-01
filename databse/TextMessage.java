import java.util.*;
import java.io.*;

public class TextMessage implements MessageInterface{

    private String message
    private User sender;
    private User receiver;
    private User[] receivers;

    public TextMessage(String message, User sender, User receiver, User[] receivers) {
        this.message = message;
        this.user = user;
        this.receiver = receiver;
        this.receivers = receivers;
    }

    public String getMessage() {
        return this.message;
    }

    public User getUser() {
        return user;
    }

    public User getReceiver(){
        return receiver;
    }

    public String[] getReceivers(){
        return receivers;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setReceivers(User[] receivers){
        this.receivers = receivers;
    }

}
