import java.util.*;
import java.io.*;

/**
 * Team Project -- UserDatabase
 *
 * Creates a database for User
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public class UserDatabase implements UserDatabaseInterface {
    // Fields
    private ArrayList<User> users;
    final private String filename = "UsersList.txt";
    private ArrayList<String> userFiles;

    // Constructor
    public UserDatabase() {
        this.users = new ArrayList<User>();
        this.userFiles = new ArrayList<String>();
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
    public boolean addUser(User user) {
        users.add(user);
        return usersToFile();
    }

    // addUser method
    public boolean removeUser(User user) {
        users.remove(user);
        return usersToFile();
    }

    // saveUser method
    public boolean usersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("filename", false))) {
            for (User user : users) {
                writer.write(user.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing users to file: " + e.getMessage());
        }
        return true;
    }
} // End of class