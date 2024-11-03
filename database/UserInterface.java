import java.util.ArrayList; // Specify imports to avoid wildcard imports

/**
 * Team Project -- UserInterface
 *
 * Defines an interface for user operations, including methods for managing user details, 
 * friendships, blocking, and messaging.
 * 
 * Authors: Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * Version: Nov 3, 2024
 */

public interface UserInterface {

    /**
     * Retrieves the username of the user.
     * 
     * @return the username as a String
     */
    String getUsername();

    /**
     * Sets a new username for the user.
     * 
     * @param username the new username as a String
     */
    void setUsername(String username);

    /**
     * Sets a new password for the user.
     * 
     * @param password the new password as a String
     */
    void setPassword(String password);

    /**
     * Retrieves the user's password.
     * 
     * @return the password as a String
     */
    String getPassword();

    /**
     * Checks if the user's profile is public.
     * 
     * @return true if the profile is public; false if it is private
     */
    boolean isPublic();

    /**
     * Sets the visibility of the user's profile.
     * 
     * @param aPublic true to make the profile public; false to make it private
     */
    void setPublic(boolean aPublic);

    /**
     * Retrieves the list of friends of the user.
     * 
     * @return an ArrayList of User objects representing friends
     */
    ArrayList<User> getFriends();

    /**
     * Sets the list of friends for the user.
     * 
     * @param friends an ArrayList of User objects to set as the user's friends
     */
    void setFriends(ArrayList<User> friends);

    /**
     * Retrieves the list of blocked users.
     * 
     * @return an ArrayList of User objects representing blocked users
     */
    ArrayList<User> getBlockedUsers();

    /**
     * Sets the list of blocked users.
     * 
     * @param blockedUsers an ArrayList of User objects to set as blocked users
     */
    void setBlockedUsers(ArrayList<User> blockedUsers);

    /**
     * Adds a friend to the user's friend list.
     * 
     * @param u the User object to add as a friend
     * @return true if the friend was successfully added; false otherwise
     */
    boolean addFriend(User u);

    /**
     * Removes a friend from the user's friend list.
     * 
     * @param u the User object to remove from the friend list
     * @return true if the friend was successfully removed; false otherwise
     */
    boolean removeFriend(User u);

    /**
     * Blocks a user, adding them to the blocked user list.
     * 
     * @param u the User object to block
     * @return true if the user was successfully blocked; false otherwise
     */
    boolean blockUser(User u);

    /**
     * Sends a text message to another user.
     * 
     * @param person the User object representing the message recipient
     * @param message the message content as a String
     * @return true if the message was successfully sent; false otherwise
     */
    boolean sendMessage(User person, String message);

    // Future methods for sending and deleting photo messages can be implemented if needed.
    // These are currently commented out to maintain focus on core messaging functionality.
    // public boolean sendPhotoMessage(User person, String message, String photo);
    
    /**
     * Deletes a text message from the conversation with a specified user.
     * 
     * @param person the User object representing the conversation partner
     * @param message the message content as a String
     */
    void deleteMessage(User person, String message);

    // public void deletePhotoMessage(PhotoMessage photo);

    /**
     * Compares this User object with another object for equality based on user details.
     * 
     * @param o the object to compare
     * @return true if the objects are equivalent; false otherwise
     */
    boolean equals(Object o);
}
