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
    private ArrayList<String> userFiles;
    private ArrayList<BufferedWriter> writers;

    // Constructor
    public UserDatabase() {
        this.users = new ArrayList<User>();
        this.userFiles = new ArrayList<String>();
        this.writers = new ArrayList<>();
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
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                System.err.println("User with this username already exists.");
                return false;
            }
        }
        String file = String.format("%s.txt", user.getUsername());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            users.add(user);
            userFiles.add(file);
            writers.add(writer);
            if (!everythingToFile()) {
                users.remove(user);
                userFiles.remove(file);
                writers.remove(writer);
                writer.close();
                return false;
            }
        } catch(IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
            if (writer != null) {
                try {
                    writer.close();
                } catch(IOException ex) {
                    System.err.println("Error closing writer during rollback: " + ex.getMessage());
                }
            }
            return false;
        }
        return true;
    }


    // removeUser method
    public boolean removeUser(User user) throws IOException {
        int index = users.indexOf(user);
        if (index == -1) {
            System.err.println("User not found in the database.");
            return false;
        }
        users.remove(index);
        String fileToRemove = userFiles.remove(index);
        try {
            BufferedWriter writer = writers.remove(index);
            writer.close();
        } catch(IOException e) {
            System.err.println("Error closing writer during user removal: " + e.getMessage());
            return false;
        }
        return everythingToFile();
    }


    // everythingToFile method
    public boolean everythingToFile() throws IOException {
        if (users.size() != userFiles.size() || users.size() != writers.size()) {
            System.err.println("Inconsistent data structure sizes in UserDatabase.");
            return false;
        }
        String filename = "UsersList.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            for (User user : users) {
                writer.write(user.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing users to file: " + e.getMessage());
            return false;
        }
        try {
            for (int i = 0; i < users.size(); i++) {
                BufferedWriter writer = writers.get(i);
                for (TextMessage message : users.get(i).getMessages()) {
                    writer.write(message.toString() + System.lineSeparator());
                }
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Error writing messages to file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void closeAllWriters() {
        for (BufferedWriter writer : writers) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
        writers.clear();
    }
} // End of class