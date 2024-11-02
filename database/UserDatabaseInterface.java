import java.util.*;

public interface UserDatabaseInterface {
    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);
    public boolean addUser(User user);
    public boolean removeUser(User user);
    public boolean usersToFile();
}