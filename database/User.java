import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Team Project -- User
 *
 * Represents a User in the system with capabilities to manage friends,
 * block users, send messages, and manage privacy settings.
 *
 * Authors: Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * Version: Nov 3, 2024
 */

public class User implements UserInterface {
    // User's unique username
    private String username;
    
    // User's password
    private String password;
    
    // List of friends associated with the user
    private ArrayList<User> friends;
    
    // List of blocked users
    private ArrayList<User> blockedUsers;
    
    // Indicates whether the user's profile is public
    private boolean isPublic;
    
    // List of text messages sent or received by the user
    private ArrayList<TextMessage> messages;
    
    // List of photo messages sent or received by the user
    private ArrayList<PhotoMessage> photos;

    // Creates an Object for synchronization purposes
    Object obj = new Object();

    /**
     * Constructor: Initializes a User object with the specified username, password, and public status.
     *
     * @param username the username for the user
     * @param password the password for the user
     * @param isPublic indicates if the user's profile is public
     * @throws BadException if the username or password does not meet the specified criteria
     */
    public User(String username, String password, boolean isPublic) throws BadException {
        if (username == null) {
            throw new BadException("Usernames can't be empty");
        }
        for (char c : username.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '_')) {
                throw new BadException("Usernames can only contain letters, numbers, or underscores.");
            }
        }
        if (username.length() > 20 || username.contains(" ")) {
            throw new BadException("Username cannot contain more than 20 characters or contain spaces");
        }

        this.username = username;

        if (password.length() < 8) {
            throw new BadException("You need at least eight characters in your password");
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
        if (!hasUpperCase || !hasDigit || !hasSpecialCharacter) {
            throw new BadException("You need at least eight characters, at least one uppercase letter, one digit, and one special character");
        }

        this.password = password;
        this.isPublic = isPublic;
        this.friends = new ArrayList<User>();
        this.blockedUsers = new ArrayList<User>();
        this.messages = new ArrayList<TextMessage>();
        this.photos = new ArrayList<PhotoMessage>();
    }

    // Getter and setter methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public ArrayList<TextMessage> getMessages() {
        return messages;
    }

    /**
     * Adds a friend to the user's friend list after verifying the user's existence.
     *
     * @param u the User to be added as a friend
     * @return true if the user was added successfully; false otherwise
     */
    public boolean addFriend(User u) {
        synchronized(obj) {
            for (User user : friends) {
                if (user.equals(u)) {
                    return false;
                }
            }
            friends.add(u);
            return true;
        } 
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param u the User to be removed from the friend list
     * @return true if the user was removed successfully; false otherwise
     */
    public boolean removeFriend(User u) {
        synchronized(obj) {
            boolean exists = false;
        for (User a : this.friends) {
            if (a.equals(u)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            friends.remove(u);
            return true;
        }
        return false;
        }
    }

    /**
     * Blocks a user, preventing them from sending messages or viewing the user's profile.
     *
     * @param u the User to be blocked
     * @return true if the user was blocked successfully; false otherwise
     */
    public boolean blockUser(User u) {
        synchronized(obj) {
            for (User user : blockedUsers) {
                if (user.equals(u)) {
                    return false;
                }
            }
            blockedUsers.add(u);
            return true;
        }
    }

    /**
     * Sends a message to another user if they are not blocked and the sender has permission.
     *
     * @param person the User to whom the message is sent
     * @param message the content of the message
     * @return true if the message was sent successfully; false otherwise
     */
    public boolean sendMessage(User person, String message) {
        synchronized(obj) {
            if (person.hasBlocked(this) || (!person.isPublic && !person.hasFriended(this))) {
                return false;
            }
            TextMessage m = new TextMessage(message, this, person);
            this.messages.add(m);
            person.messages.add(m);
            //TextMessage.id++;
            return true;
        }
    }

    /**
     * Deletes a message from both the sender and receiver's message list.
     *
     * @param message the TextMessage to be deleted
     * @return true if the message was deleted from both users; false otherwise
     */
    public boolean deleteMessage(TextMessage message) {
        synchronized(obj) {
            boolean exists1 = false;
            for (int i = 0; i < this.messages.size(); i++) {
                if (this.messages.get(i).equals(message)) {
                    this.messages.remove(i);
                    exists1 = true;
                    break;
                }
            }
            boolean exists2 = false;
            for (int i = 0; i < message.getReceiver().messages.size(); i++) {
                if (message.getReceiver().messages.get(i).equals(message)) {
                    message.getReceiver().messages.remove(i);
                    exists2 = true;
                    break;
                }
            }
            return exists1 && exists2;
        }   
    }

    /**
     * Sends a message to another user if they are not blocked and the sender has permission.
     *
     * @param person the User to whom the message is sent
     * @param message the content of the message
     * @return true if the message was sent successfully; false otherwise
     */
    public boolean sendPhotoMessage(User person, String message, String photo) {
        synchronized(obj) {
            if (person.hasBlocked(this) || (person.isPublic && !person.hasFriended(this))) {
                return false;
            }
            PhotoMessage m = new PhotoMessage(message, this, person, photo);
            this.photos.add(m);
            person.photos.add(m);
    
            return true;
        }
    }

    /**
     * Deletes a message from both the sender and receiver's message list.
     *
     * @param message the PhotoMessage to be deleted
     * @return true if the message was deleted from both users; false otherwise
     */
    public void deletePhotoMessage(PhotoMessage photo) {
        synchronized(obj) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).equals(photo)) {
                    photos.remove(i);
                    break;
                }
            }
            for (int i = 0; i < photo.getReceiver().photos.size(); i++) {
                if (photo.getReceiver().photos.get(i).equals(photo)) {
                    photo.getReceiver().photos.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Compares this User to another object for equality based on the username.
     *
     * @param o the object to compare
     * @return true if the object is a User with the same username; false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            return u.username.equals(this.username);

        }
        return false;

    }

    /**
     * Checks if the user has blocked a specific user.
     *
     * @param u the User to check
     * @return true if the user is blocked; false otherwise
     */
    public boolean hasBlocked(User u) {
        for (int i = 0; i < blockedUsers.size(); i++) {
            if (blockedUsers.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user has added a specific user as a friend.
     *
     * @param u the User to check
     * @return true if the user is a friend; false otherwise
     */
    public boolean hasFriended(User u) {
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the User object, including username, password,
     * privacy setting, friends, and blocked users.
     *
     * @return a formatted string of the User's information
     */
    public String toString() {
        String line = "";
        line += this.username + "," + this.password + "," + this.isPublic + ",";
        for (User u : friends) {
            line += u.getUsername() + ",";

        }
        line += "End of Friends,";
        for(User u : blockedUsers) {
            line += u.getUsername() + ",";
        }
        return line;
    }

}