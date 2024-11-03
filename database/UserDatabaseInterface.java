import java.io.IOException;
import java.util.*;

/**
 * Team Project -- UserDatabaseInterface
 *
 * Interface for UserDatabase, creates the methods for the class
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public interface UserDatabaseInterface {
    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);
    boolean addUser(User user) throws IOException;
    boolean removeUser(User user) throws IOException;
    boolean everythingToFile() throws IOException;
    void closeAllWriters();
}