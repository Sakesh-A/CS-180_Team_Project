import java.util.*;

public interface UserInterface {
    public String getUsername();
    public void setUsername(String username);
    public void setPassword(String password);
    public String getPassword();
    public boolean isPublic();
    public void setPublic(boolean aPublic);
    public ArrayList<User> getFriends();
    public void setFriends(ArrayList<User> friends);
    public ArrayList<User> getBlockedUsers();
    public void setBlockedUsers(ArrayList<User> blockedUsers);
    public boolean addFriend(User u);
    public boolean removeFriend(User u);
    public boolean blockUser(User u);

    public boolean sendMessage(User person, String message);
    public boolean sendPhotoMessage(User person, String message, String photo);

    public void deleteMessage(TextMessage message);
    public void deletePhotoMessage(PhotoMessage photo);
    public boolean equals(Object o);
}
