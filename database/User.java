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
        this.friends = new ArrayList<User>();
        this.blockedUsers = new ArrayList<User>();
        this.messages = new ArrayList<TextMessage>();
        this.photos = new ArrayList<PhotoMessage>();
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
    public ArrayList<TextMessage> getMessages() {
        return messages;
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
        TextMessage m = new TextMessage(message, this, person);
        this.messages.add(m);
        person.messages.add(m);
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
        PhotoMessage m = new PhotoMessage(message, this, person, photo);
        this.photos.add(m);
        person.photos.add(m);
        TextMessage.id++;
        return true;
    }

    public void deleteMessage(TextMessage message) {
        for (int i = 0; i < this.messages.size(); i++) {
            if (this.messages.get(i).equals(message)) {
                this.messages.remove(i);
                break;
            }
        }
        for (int i = 0; i < message.getReceiver().messages.size(); i++) {
            if (message.getReceiver().messages.get(i).equals(message)) {
                message.getReceiver().messages.remove(i);
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
        for (int i = 0; i < photo.getReceiver().photos.size(); i++) {
            if (photo.getReceiver().photos.get(i).equals(photo)) {
                photo.getReceiver().photos.remove(i);
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
