import java.util.*;

/**
 * Team Project -- UserInterface
 *
 * Interface for UserInterface, creates the methods for the class
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public interface UserInterface {
    String getUsername();
    void setUsername(String username);
    void setPassword(String password);
    String getPassword();
    boolean isPublic();
    void setPublic(boolean aPublic);
    ArrayList<User> getFriends();
    void setFriends(ArrayList<User> friends);
    ArrayList<User> getBlockedUsers();
    void setBlockedUsers(ArrayList<User> blockedUsers);
    boolean addFriend(User u);
    boolean removeFriend(User u);
    boolean blockUser(User u);

    boolean sendMessage(User person, String message);
//    public boolean sendPhotoMessage(User person, String message, String photo);

    void deleteMessage(TextMessage message);
//    public void deletePhotoMessage(PhotoMessage photo);
    boolean equals(Object o);
}
