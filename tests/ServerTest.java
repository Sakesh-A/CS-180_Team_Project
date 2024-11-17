import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ServerTest {

    private User mockUser;

    @Before
    public void setUp() {
        // Set up a mock user object for testing
        mockUser = new User("user1", "password", true);  // Username, password, and public profile
    }

    @Test
    public void testClassExistence() {
        // Verifying that the Server class exists
        assertNotNull(Server.class);
    }

    @Test
    public void testConstructor() {
        // Test if the Server class can be instantiated without issues
        try {
            new Server();  // Server does not have a custom constructor, so this is implicitly tested.
        } catch (Exception e) {
            fail("Server class instantiation failed: " + e.getMessage());
        }
    }

    @Test
    public void testFieldsExistence() {
        // Verifying the existence of key fields in the Server class
        try {
            // Verifying critical fields in the Server class
            assertNotNull(Server.class.getDeclaredField("userDatabase"));
            assertNotNull(Server.class.getDeclaredField("loggedInUsers"));
            assertNotNull(Server.class.getDeclaredField("loginLock"));
        } catch (NoSuchFieldException e) {
            fail("Field not found: " + e.getMessage());
        }
    }

    @Test
    public void testAddLoggedInUser() {
        // Verifying if a user can be added to the logged-in users list
        boolean added = Server.addLoggedInUser(mockUser);
        assertTrue("User should be added to the logged-in users list.", added);

        // Verify that adding the same user again will fail (should not be added twice)
        boolean addedAgain = Server.addLoggedInUser(mockUser);
        assertFalse("User should not be added twice.", addedAgain);
    }

    @Test
    public void testAddLoggedInUserAlreadyLoggedIn() {
        // Prepare a user and add it to the logged-in list
        Server.addLoggedInUser(mockUser);

        // Attempt to add the same user again
        boolean result = Server.addLoggedInUser(mockUser);
        assertFalse("The user should not be added twice.", result);
    }

    @Test
    public void testRemoveLoggedInUser() {
        // Add a user to the logged-in users list
        Server.addLoggedInUser(mockUser);

        // Remove the user
        Server.removeLoggedInUser(mockUser);

        // Verify that the user is no longer in the logged-in users list
        List<User> loggedInUsers = Server.getLoggedInUsers(); // We assume a getter method for the logged-in users
        assertFalse("User should be removed from the logged-in users list.", loggedInUsers.contains(mockUser));
    }

    @Test
    public void testRemoveLoggedInUserWhenNotLoggedIn() {
        // Try removing a user who is not logged in
        Server.removeLoggedInUser(mockUser);
        // No exceptions should be thrown, and it should handle gracefully
        // Verifying that no exception is thrown
        try {
            Server.removeLoggedInUser(mockUser);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testNoDuplicatesInLoggedInUsers() {
        // Add the same user twice and check for duplicates
        Server.addLoggedInUser(mockUser);
        Server.addLoggedInUser(mockUser); // Try adding again

        List<User> loggedInUsers = Server.getLoggedInUsers();  // We assume a getter method for logged-in users
        long userCount = loggedInUsers.stream().filter(user -> user.getUsername().equals("user1")).count();

        // Assert that only one user with the same username exists in the list
        assertEquals("There should only be one user with the username 'user1'.", 1, userCount);
    }

    @Test
    public void testAddUserWithNullUsername() {
        // Try adding a user with null username (edge case)
        User nullUser = new User(null, "password", true);
        boolean added = Server.addLoggedInUser(nullUser);

        assertFalse("User with null username should not be added.", added);
    }

    @Test
    public void testAddUserWithEmptyUsername() {
        // Try adding a user with an empty username
        User emptyUser = new User("", "password", true);
        boolean added = Server.addLoggedInUser(emptyUser);

        assertFalse("User with empty username should not be added.", added);
    }

    @Test
    public void testThreadSafetyOfAddLoggedInUser() throws InterruptedException {
        // Simulate concurrent access by two threads adding the same user
        Thread thread1 = new Thread(() -> {
            User user1 = new User("user1", "password1", true);
            Server.addLoggedInUser(user1);
        });

        Thread thread2 = new Thread(() -> {
            User user2 = new User("user1", "password2", true);  // Same username to test thread safety
            Server.addLoggedInUser(user2);
        });

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // Only one user should be added to the logged-in users list
        List<User> loggedInUsers = Server.getLoggedInUsers(); // Assuming a getter is added for testing
        assertEquals("Only one user should be logged in.", 1, loggedInUsers.size());
    }
}

