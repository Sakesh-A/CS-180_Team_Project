import java.util.*;

public interface UserInterface {
    public String getUsername();
    public void setUsername(String username);
    public void setPassword(String password);
    public String getPassword();
    public boolean isPrivateOrPublic();
    public void setPrivateOrPublic(boolean privateOrPublic);
    public ArrayList<User> getFriends();
    public void setFriends(ArrayList<User> friends);
    public ArrayList<User> getBlockedUsers();
    public void setBlockedUsers(ArrayList<User> blockedUsers);
    public boolean addFriend(User u);
    public boolean removeFriend(User u);
    public boolean blockUser(User u);
    public void sendMessage(User person, TextMessage message);
    public void sendPhotoMessage(User person, PhotoMessage photo);
    public void deleteMessage(User person, TextMessage message);
    public void deletePhotoMessage(User person, PhotoMessage photo);
    public boolean equals(Object o);
}
