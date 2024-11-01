import java.util.*;
import java.io.*;

/**
 * Team Project -- PhotoMessage
 *
 * Creates a PhotoMessage object and includes gets/sets
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public class PhotoMessage extends TextMessage {



    private String photo;


    public PhotoMessage(String message, User sender, User receiver, User[] receivers, String photo) {
        super(message, sender, receiver, receivers);
        this.photo = photo;
    }


    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
