import java.util.*;
import java.io.*;

public interface MessageInterface {
    public String getMessageSent();
    public String getMessageRecieved();
    public void setMessageSent(String messageSent);
    public void setMessageRecieved(String messageRecieved);
}
