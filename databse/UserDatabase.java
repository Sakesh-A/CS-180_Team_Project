import java.util.*;

public class UserDatabase implements UserDatabaseInterface {
    // Fields
    private ArrayList<User> users;

    // Constructor
    public UserDatabase(ArrayList<User> users) {
        this.users = users;
    }

    // Getter methods
    public ArrayList<User> getUsers() {
        return users;
    }

    // Setter methods
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    // addUser method
    public void addUser(User user) {
        users.add(user);
    }

} // End of class