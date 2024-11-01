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
    public String getMessageSent();
    public String getMessageReceived();
    public void setMessageSent(String messageSent);
    public void setMessageReceived(String messageReceived);
}
