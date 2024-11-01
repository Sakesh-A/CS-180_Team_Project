import java.util.*;
import java.io.*;

public class User implements UserInterface{
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<User> blockedUsers;
    private boolean privateOrPublic;

    public User(String username, String password, boolean privateOrPublic) {
        this.username = username;
        this.password = password;
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
        friends.add(u);
        return true;
    }
    public boolean removeFriend(User u) {
        friends.remove(u);
        return true;
    }
    public boolean blockUser(User u) {
        friends.add(u);
        return true;
    }

    public void sendMessage(User u) {

    }

    public void receiveMessage() {

    }

    public void deleteMessage(TextMessage m) {

    }

    public void deleteMessage(PhotoMessage m) {
        
    }
}
