import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
        assertFalse("Message should not be sent to blocked user", user2.blockUser(user1));
        assertFalse("Message should not be sent due to privacy", user2.sendMessage(user1, "Hello, Alice!"));
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
        String expectedString = "Alice,Password1!,true,Bob,End of Friends";
        assertEquals("toString should return formatted string", expectedString, user1.toString());
    }
}

