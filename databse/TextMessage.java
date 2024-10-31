public class TextMessage implements MessageInterface{


    private String user;
    private String reciever;
    private String[] recievers;
    private String messageSent;
    private String messageRecieved;



    public TextMessage(String user, String reciever, String[] recievers, String messageSent, String messageRecieved) {
        this.user = user;
        this.reciever = reciever;
        this.recievers = recievers;
        this.messageSent = messageSent;
        this.messageRecieved = messageRecieved;


    }


    public String getUser() {
        return user;
    }

    public String getReciever(){
        return reciever;
    }

    public String[] getRecievers(){
        return recievers;
    }

    public String getMessageSent(){
        return messageSent;
    }

    public String getMessageRecieved(){
        return messageRecieved;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setReciever(String reciever){
        this.reciever = reciever;
    }

    public void setRecievers(String[] recievers){
        this.recievers = recievers;
    }

    public void setMessageSent(String messageSent){
        this.messageSent = messageSent;
    }


    public void setMessageRecieved(String messageRecieved){
        this.messageRecieved = messageRecieved;
    }
    


}
