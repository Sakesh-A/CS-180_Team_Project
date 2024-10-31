import java.util.*;
import java.io.*;

public class TextMessage implements MessageInterface{

    public void blockUser(User u);
    private String user;
    private String receiver;
    private String[] receivers;
    private String messageSent;
    private String messageReceived;



    public TextMessage(String user, String receiver, String[] receivers, String messageSent, String messageReceived) {
        this.user = user;
        this.receiver = receiver;
        this.receivers = receivers;
        this.messageSent = messageSent;
        this.messageReceived = messageReceived;


    }


    public String getUser() {
        return user;
    }

    public String getReceiver(){
        return receiver;
    }

    public String[] getReceivers(){
        return receivers;
    }

    public String getMessageSent(){
        return messageSent;
    }

    public String getMessageReceived(){
        return messageReceived;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public void setReceivers(String[] receivers){
        this.receivers = receivers;
    }

    public void setMessageSent(String messageSent){
        this.messageSent = messageSent;
    }


    public void setMessageReceived(String messageReceived){
        this.messageReceived = messageReceived;
    }



}
