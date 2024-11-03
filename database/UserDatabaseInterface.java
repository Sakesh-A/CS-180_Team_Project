import java.io.IOException;
import java.util.*;

public interface UserDatabaseInterface {
    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);
    boolean addUser(User user) throws IOException;
    boolean removeUser(User user) throws IOException;
    boolean everythingToFile() throws IOException;
    void closeAllWriters();
}