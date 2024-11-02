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
    public boolean saveUser(String username, String password, boolean allowMessagesFromAnyone) {
        if (!isPasswordValid(password)) {
            return false;
        }

        User newUser = new User(username, password, allowMessagesFromAnyone);

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

    // isPasswordValid method (private method for saveUser)
    public boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        return (hasUpperCase && hasDigit && hasSpecialCharacter) ;
    }

//test code
 
} // End of class