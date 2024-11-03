import java.util.*;
import java.io.*;

/**
 * Team Project -- UserDatabase
 *
 * Manages a database of User objects, providing methods to add, remove, and save users 
 * and their messages to file. Maintains consistency between users, user files, 
 * and writer objects.
 *
 * Authors: Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * Version: Nov 3, 2024
 */

public class UserDatabase implements UserDatabaseInterface {

    // List of all users in the database
    private ArrayList<User> users;
    
    // List of file names for each user's data
    private ArrayList<String> userFiles;
    
    // List of BufferedWriter objects for each user's file
    private ArrayList<BufferedWriter> writers;

    /**
     * Constructor: Initializes the lists for users, file names, and file writers.
     */
    public UserDatabase() {
        this.users = new ArrayList<>();
        this.userFiles = new ArrayList<>();
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

    /**
     * Adds a new user to the database and saves the user's data to a file.
     * Ensures the username is unique in the database.
     *
     * @param user the User object to add
     * @return true if the user was successfully added; false otherwise
     */
    public boolean addUser(User user) {
        // Check if username already exists
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                System.err.println("User with this username already exists.");
                return false;
            }
        }
        
        // Create a file for the user
        String file = String.format("%s.txt", user.getUsername());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            users.add(user);
            userFiles.add(file);
            writers.add(writer);
            
            // Write all users to "UsersList.txt"
            if (!everythingToFile()) {
                // Roll back if saving fails
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

    /**
     * Removes a user from the database, including their data file and writer.
     *
     * @param user the User object to remove
     * @return true if the user was successfully removed; false otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean removeUser(User user) throws IOException {
        int index = users.indexOf(user);
        if (index == -1) {
            System.err.println("User not found in the database.");
            return false;
        }
        
        // Remove user and associated resources
        users.remove(index);
        String fileToRemove = userFiles.remove(index);
        try {
            BufferedWriter writer = writers.remove(index);
            writer.close();
        } catch(IOException e) {
            System.err.println("Error closing writer during user removal: " + e.getMessage());
            return false;
        }
        
        // Update "UsersList.txt"
        return everythingToFile();
    }

    /**
     * Writes all users and their messages to files, ensuring consistency across all files.
     *
     * @return true if the operation was successful; false otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean everythingToFile() throws IOException {
        if (users.size() != userFiles.size() || users.size() != writers.size()) {
            System.err.println("Inconsistent data structure sizes in UserDatabase.");
            return false;
        }
        
        // Write user list to "UsersList.txt"
        String filename = "UsersList.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            for (User user : users) {
                writer.write(user.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing users to file: " + e.getMessage());
            return false;
        }

        // Write each user's messages to their corresponding file
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

    /**
     * Closes all open BufferedWriter objects and clears the writers list.
     */
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
