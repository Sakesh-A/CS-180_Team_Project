public class PhotoMessage extends TextMessage {



    private String photo;


    public PhotoMessage(String user, String reciever, String[] recievers, String messageSent, String messageRecieved, String photo) {
        super(user, reciever, recievers, messageSent, messageRecieved);
        this.photo = photo;


    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
