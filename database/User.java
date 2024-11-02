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
        this.username = username; // cannot be null, under 20 characters, no spaces,
        // only capital/lowercase, letters, underscore, and numbers
        this.password = password; // cannot be null, atleast 8 characters, no spaces,
        // has to have at least one capital letter, one number, and one special character
        this.privateOrPublic = privateOrPublic;
    }

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

    public boolean sendMessage(User person, String message) {
        if (person.hasBlocked(this) || (person.privateOrPublic && !person.hasFriended(this))) {
            return false;
        }
        this.messages.add(new TextMessage(message, this, person));
        person.messages.add(new TextMessage(message, this, person));
        TextMessage.id++;
        return true;
    }
    public boolean hasBlocked(User u) {
        for (int i = 0; i < blockedUsers.size(); i++) {
            if (blockedUsers.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasFriended(User u) {
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }

    public boolean sendPhotoMessage(User person, String message, String photo) {
        if (person.hasBlocked(this) || (person.privateOrPublic && !person.hasFriended(this))) {
            return false;
        }
        this.messages.add(new PhotoMessage(message, this, person, photo));
        person.messages.add(new PhotoMessage(message, this, person, photo));
        TextMessage.id++;
        return true;
    }

    public void deleteMessage(TextMessage message) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).equals(message)) {
                messages.remove(i);
                break;
            }
        }
    }

    public void deletePhotoMessage(PhotoMessage photo) {
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).equals(photo)) {
                photos.remove(i);
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
