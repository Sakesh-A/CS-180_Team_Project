import java.util.*;
import java.io.*;

/**
 * Team Project -- MessageInterface
 *
 * Creates an interface for Message
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public interface MessageInterface {
    public String getMessage();
    public User getReceiver();
    public User getSender();
    public void setMessage(String message);
    public void setReceiver(User receiver);
    public void setSender(User sender);

}
