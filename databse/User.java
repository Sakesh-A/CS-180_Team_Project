import java.util.*;
import java.io.*;

public class User implements UserInterface {
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<User> blockedUsers;
    private boolean privateOrPublic;
    private ArrayList<TextMessage> messages;
    private ArrayList<PhotoMessage> photos;

    public User(String username, String password, boolean privateOrPublic) {
        if (username == null || username.length() > 20 || username.contains(" ") ||
                !username.matches("[a-zA-Z0-9_]+")) {
            throw BadException("Username cannot be null, must be under 20 characters, " +
                    "cannot contain spaces, and can only contain letters, numbers, " +
                    "and underscores.");
        }
        this.username = username;

        if (password == null || password.length() < 8 || password.contains(" ") ||
                !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") ||
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw BadException("Password cannot be null, must be at least 8 characters, " +
                    "cannot contain spaces, must contain at least one capital letter, " +
                    "one number, and one special character.");
        }
        this.password = password;

        this.privateOrPublic = privateOrPublic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.length() > 20 || username.contains(" ") ||
                !username.matches("[a-zA-Z0-9_]+")) {
            throw BadException("Username cannot be null, must be under 20 characters, " +
                    "cannot contain spaces, and can only contain letters, numbers, " +
                    "and underscores.");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8 || password.contains(" ") ||
                !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") ||
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw BadException("Password cannot be null, must be at least 8 characters, " +
                    "cannot contain spaces, must contain at least one capital letter, " +
                    "one number, and one special character.");
        }
        this.password = password;
    }

    public boolean isPrivateOrPublic() {
        return privateOrPublic;
    }

    public void setPrivateOrPublic(boolean privateOrPublic) {
        this.privateOrPublic = privateOrPublic;
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

    public boolean addFriend(User u) {
        for(User user : friends){
            if(user.equals(u)) {
                return false;
            }
        }
        friends.add(u);
        return true;
    }

    public boolean removeFriend(User u) {
        boolean exists = false;
        for (User a : this.friends) {
            if (a.equals(u)) {
                exists = true;
            }
        }
        if (exists) {
            friends.remove(u);
            return true;
        }
        return false;
    }

    public boolean blockUser(User u) {
        blockedUsers.add(u);
        return true;
    }

    public void sendMessage(User person, String message) {
        this.messages.add(new TextMessage(message, this, person));
        person.messages.add(new TextMessage(message, this, person));
    }

    public void sendPhotoMessage(User person, PhotoMessage photo) {

    }

    public void deleteMessage(TextMessage message) {
        for (TextMessage t : messages) {
            if (t.equals(message)) {
                messages.remove(message);
                break;
            }
        }
    }

    public void deletePhotoMessage(PhotoMessage photo) {
        for (PhotoMessage p : photos) {
            if (p.equals(photo)) {
                photos.remove(photo);
                break;
            }
        }
    }

    public boolean equals(Object o){
        if(o instanceof User){
            User u = (User) o;
          if(u.password.equals(this.password) && u.username.equals(this.username)) {
                 return true;
          }
          return false;

        }
        return false;

    }
}

