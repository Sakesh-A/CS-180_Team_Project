import java.util.*;
import java.io.*;

public interface MessageInterface {
    public String getMessageSent();
    public String getMessageReceived();
    public void setMessageSent(String messageSent);
    public void setMessageReceived(String messageReceived);
}
