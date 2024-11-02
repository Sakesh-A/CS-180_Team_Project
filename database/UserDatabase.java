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

    // Constructor
    public UserDatabase() {
        this.users = new ArrayList<User>();
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

    // saveUser method
    public boolean saveUser(User newUser) {
        users.add(newUser);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UsersList.txt", true))) {
            writer.write(username + "," + password + "," + allowMessagesFromAnyone + "\n");
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }

        return true;
    }

    // validateLogin method
    public boolean validateLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }
 
} // End of class