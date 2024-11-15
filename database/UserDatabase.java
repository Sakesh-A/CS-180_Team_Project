package database;
import java.util.*;
import java.io.*;

/**
 * Team Project -- UserDatabase
 *
 * Creates a database for User
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */

public class UserDatabase extends Thread implements UserDatabaseInterface {
    // Fields
    private ArrayList<User> users;
    private ArrayList<String> userFiles;
    private ArrayList<BufferedWriter> writers;
    Object obj = new Object();

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
        synchronized(obj) {
            for (User existingUser : users) {
                if (existingUser.getUsername().equals(user.getUsername())) {
                    //System.err.println("User with this username already exists.");
                    //throw new BadException("Username already exists");
                    return false;
                }
            }
            users.add(user);
            String file = String.format("%s.txt", user.getUsername());
            userFiles.add(file);
            try {
                //writers.add(new BufferedWriter(new FileWriter(file)));
                BufferedWriter br = new BufferedWriter(new FileWriter(file));
            } catch(IOException e) {

                return false;
            }
            return true;
        }
    }

    // removeUser method
    public boolean removeUser(User user) {
        synchronized(obj) {
            boolean exists = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(user)) {
                    users.remove(i);
                    exists = true;
                }
            }
            if (!exists) {
                return false;
            }
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).hasFriended(user)) {
                    users.get(i).removeFriend(user);
                }
            }

            for(int i = 0; i < userFiles.size(); i++) {
                String temp = userFiles.get(i).substring(0, userFiles.get(i).lastIndexOf("."));
                if(user.getUsername().equals(temp)) {
                    userFiles.remove(userFiles.get(i));
                    //writers.remove(i);
                }
            }
            return true;
        }
    }

    // saveUser method
    public boolean everythingToFile() {
        synchronized(obj) {
            String filename = "UsersList.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
                for (User user : users) {
                    writer.write(user.toString() + "\n");
                }
            } catch (IOException e) {
                System.err.println("Error writing users to file: " + e.getMessage());
                return false;
            }

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                String name = user.getUsername() + ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(name, false))) {

                    ArrayList<TextMessage> messages = user.getMessages();
                    for (TextMessage message : messages) {
                        writer.write(message.toString());
                    }
                } catch(IOException e) {
                    System.err.println("Error writing users to file: " + e.getMessage());
                    return false;
                }
            }
            return true;
        }
    }
}
