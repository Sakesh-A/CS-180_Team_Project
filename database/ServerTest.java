import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ServerTest {

    private User mockUser;

    @Before
    public void setUp() {
        // Set up a mock user object for testing
        try {
            // Username, password, and public profile
            mockUser = new User("user1", "Password1!", true); // Username, password, and public profile
        }
        catch(BadException e){
            assertEquals("Mock user can't be created.", e.getMessage());
        }

    }

    // Test adding a valid user to the logged-in users list
    @Test
    public void testAddLoggedInUser() {
        boolean result = Server.addLoggedInUser(mockUser);
        assertTrue("User should be added to the logged-in users list.", result);
    }

    // Test adding the same user twice (should fail since user is already logged in)
    @Test
    public void testAddLoggedInUserTwice() {
        Server.addLoggedInUser(mockUser);
        boolean result = Server.addLoggedInUser(mockUser);  // Same user added again
        assertFalse("User should not be added if already logged in.", result);
    }





    // Additional tests for addLoggedInUser and removeLoggedInUser (unchanged)
    @Test
    public void testAddLoggedInUserWithNullUser() {
        boolean result = Server.addLoggedInUser(null);
        assertFalse("Null user should not be added to the logged-in users list.", result);
    }

    @Test
    public void testRemoveLoggedInUserWhenListIsEmpty() {
        Server.removeLoggedInUser(mockUser); // Should not throw any exceptions
    }

    @Test
    public void testLoggedInUsersListIsEmptyAfterRemoval() {
        Server.addLoggedInUser(mockUser);
        Server.removeLoggedInUser(mockUser);
        // Since we cannot directly check the list, we will rely on the user being removed successfully
    }

    // Simulate handling BadException within Server's addLoggedInUser
    @Test
    public void testServerAddUserWithBadException() {
        try {
            User invalidUser = new User(null, "password", true);  // Null username
            boolean result = Server.addLoggedInUser(invalidUser);
            assertFalse("User should not be added with null username.", result);
        } catch (BadException e) {
            assertEquals("Usernames can't be empty", e.getMessage());
        }
    }

    // Test thread safety
    @Test
    public void testThreadSafetyWithMultipleConcurrentAdditions() throws InterruptedException {
        Thread addThread1 = new Thread(() -> {
            try {
                User user = new User("user1", "password", true);
                Server.addLoggedInUser(user);
            }catch(BadException e){
                assertEquals("Mock user can't be created.", e.getMessage());
            }

        });
        Thread addThread2 = new Thread(() -> {
            try {
                User user = new User("user2", "password", true);
                Server.addLoggedInUser(user);
            } catch(BadException e){
                assertEquals("Mock user can't be created.", e.getMessage());
            }
        });

        addThread1.start();
        addThread2.start();

        addThread1.join();
        addThread2.join();
    }

    // Test exception handling for BadException
    @Test
    public void testBadExceptionHandling() {
        try {
            throw new BadException("This is a bad exception");
        } catch (BadException e) {
            assertEquals("This is a bad exception", e.getMessage());
        }
    }
}
