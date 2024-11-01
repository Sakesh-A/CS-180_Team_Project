import java.util.*;

public interface UserDatabaseInterface {
    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);
    void addUser(User user);
    //boolean saveUser(String username, String password, boolean allowMessagesFromAnyone);
    boolean validateLogin(String username, String password);
}