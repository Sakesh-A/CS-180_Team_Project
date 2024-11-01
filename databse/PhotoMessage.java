import java.util.*;
import java.io.*;

public class PhotoMessage extends TextMessage {



    private String photo;


    public PhotoMessage(String message, User sender, User receiver, User[] receivers, String photo) {
        super(message, sender, receiever, receivers);
        this.photo = photo;
    }


    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
