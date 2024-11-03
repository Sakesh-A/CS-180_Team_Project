import java.util.*;

public interface UserDatabaseInterface {
    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);
    boolean addUser(User user);
    boolean removeUser(User user);
    boolean everythingToFile();
}