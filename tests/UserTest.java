import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Team Project -- UserTest
 *
 * Unit tests for the User class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */
public class UserTest {

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() throws BadException {
        user1 = new User("Alice", "Password1!", true);
        user2 = new User("Bob", "Password2@", true);
        user3 = new User("Charlie", "Password3#", true);
    }

    @Test
    public void testGetUsername() {
        assertEquals("Username should be Alice", "Alice", user1.getUsername());
    }

    @Test
    public void testSetUsername() {
        user1.setUsername("AliceWonderland");
        assertEquals("Username should be updated", "AliceWonderland", user1.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("Password should be Password1!", "Password1!", user1.getPassword());
    }

    @Test
    public void testSetPassword() {
        user1.setPassword("NewPassword!");
        assertEquals("Password should be updated", "NewPassword!", user1.getPassword());
    }

    @Test
    public void testAddFriend() {
        assertTrue("User2 should be added as a friend", user1.addFriend(user2));
        assertFalse("User2 should not be added again", user1.addFriend(user2));
    }

    @Test
    public void testRemoveFriend() {
        user1.addFriend(user2);
        assertTrue("User2 should be removed as a friend", user1.removeFriend(user2));
        assertFalse("User2 should not be removed again", user1.removeFriend(user2));
    }

    @Test
    public void testBlockUser() {
        assertTrue("User2 should be blocked successfully", user1.blockUser(user2));
        assertFalse("User2 should not be blocked again", user1.blockUser(user2));
    }

    @Test
    public void testSendMessage() {
        assertTrue("Message should be sent successfully", user1.sendMessage(user2, "Hello, Bob!"));
        assertTrue("User has been blocked correctly", user2.blockUser(user1));
        assertFalse("Message should not be sent due to privacy", user1.sendMessage(user2, "Hello, Alice!"));
    }

    @Test
    public void testDeleteMessage() {
        user1.sendMessage(user2, "Hello, Bob!");
        TextMessage message = user1.getMessages().get(0);
        assertTrue("Message should be deleted successfully", user1.deleteMessage(message));
    }

    @Test
    public void testHasBlocked() {
        user1.blockUser(user2);
        assertTrue("User1 should have blocked User2", user1.hasBlocked(user2));
    }

    @Test
    public void testHasFriended() {
        user1.addFriend(user2);
        assertTrue("User1 should have friended User2", user1.hasFriended(user2));
    }

    @Test
    public void testEquals() throws BadException {
        assertEquals("User1 should be equal to another instance with the same username", user1, new User("Alice", "Password1!", true));
        assertNotEquals("User1 should not be equal to User2", user1, user2);
    }

    @Test
    public void testToString() {
        user1.addFriend(user2);
        String expectedString = "Alice,Password1!,true,Bob,End of Friends,";
        assertEquals("toString should return formatted string", expectedString, user1.toString());
    }

    

    @Test(expected = BadException.class)
    public void testInvalidUsername() throws BadException {
        new User("Alice@Home", "Password1@", true); 
    }

    @Test(expected = BadException.class)
    public void testInvalidPassword() throws BadException {
        new User("Alice", "password", true); 
    }

    @Test(expected = BadException.class)
    public void testEmptyUsername() throws BadException {
        new User("", "Password1!", true); 
    }

    @Test(expected = BadException.class)
    public void testShortPassword() throws BadException {
        new User("Alice", "short", true); 
    }

    @Test
    public void testMaxLengthUsername() throws BadException {
        User user = new User("A".repeat(20), "Password1@", true);
        assertEquals("Username should be 20 characters", "A".repeat(20), user.getUsername());
    }

    @Test(expected = BadException.class)
    public void testTooLongUsername() throws BadException {
        new User("A".repeat(21), "Password1@", true); 
    }

    @Test
    public void testAddFriendAfterBlocked() {
        user1.blockUser(user2);
        assertFalse("User2 should not be able to become a friend after being blocked", user1.addFriend(user2));
    }

    @Test
    public void testSendMessageWhenProfilePrivate() {
        user2.setPublic(false); 
        assertFalse("Message should not be sent because user's profile is private", user1.sendMessage(user2, "Hello, Bob!"));
    }

    @Test
    public void testSendMessageWhenNotFriends() {
        user2.setPublic(false);  
        assertFalse("Message should not be sent because user1 and user2 are not friends", user1.sendMessage(user2, "Hello, Bob!"));
    }

    @Test
    public void testSendMessageToBlockedUser() {
        user1.blockUser(user2);
        assertFalse("Message should not be sent to a blocked user", user1.sendMessage(user2, "This should not go through"));
    }

    @Test
    public void testRemoveFriendAfterBlocking() {
        user1.addFriend(user2);
        user1.blockUser(user2);
        assertFalse("User2 should not be removed as a friend after blocking", user1.removeFriend(user2));
    }

    @Test
    public void testSendPhotoMessage() {
        String photoMessage = "Check out this photo!";
        String photo = "photo_url";
        assertTrue("Photo message should be sent successfully", user1.sendPhotoMessage(user2, photoMessage, photo));
    }

    @Test
    public void testDeletePhotoMessage() {
        String photoMessage = "Check out this photo!";
        String photo = "photo_url";
        user1.sendPhotoMessage(user2, photoMessage, photo);
        PhotoMessage message = user1.getPhotos().get(0);
        user1.deletePhotoMessage(message);
        assertTrue("Photo message should be deleted successfully", !user1.getPhotos().contains(message));
    }

}
