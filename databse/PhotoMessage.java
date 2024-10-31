import java.util.*;
import java.io.*;

public class PhotoMessage extends TextMessage {



    private String photo;


    public PhotoMessage(String user, String receiver, String[] receivers, String messageSent, String messageReceived, String photo) {
        super(user, receiver, receivers, messageSent, messageReceived);
        this.photo = photo;


    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
