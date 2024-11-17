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
      
        mockUser = new User("user1", "password", true);  // Username, password, and public profile
    }

    @Test
    public void testClassExistence() {
       
        assertNotNull(Server.class);
    }

    @Test
    public void testConstructor() {
      
        try {
            new Server();
        } catch (Exception e) {
            fail("Server class instantiation failed: " + e.getMessage());
        }
    }

    @Test
    public void testFieldsExistence() {
      
        try {
           
            assertNotNull(Server.class.getDeclaredField("userDatabase"));
            assertNotNull(Server.class.getDeclaredField("loggedInUsers"));
            assertNotNull(Server.class.getDeclaredField("loginLock"));
        } catch (NoSuchFieldException e) {
            fail("Field not found: " + e.getMessage());
        }
    }

    @Test
    public void testAddLoggedInUser() {
       
        boolean added = Server.addLoggedInUser(mockUser);
        assertTrue("User should be added to the logged-in users list.", added);

       
        boolean addedAgain = Server.addLoggedInUser(mockUser);
        assertFalse("User should not be added twice.", addedAgain);
    }

    @Test
    public void testAddLoggedInUserAlreadyLoggedIn() {
    
        Server.addLoggedInUser(mockUser);

     
        boolean result = Server.addLoggedInUser(mockUser);
        assertFalse("The user should not be added twice.", result);
    }

    @Test
    public void testRemoveLoggedInUser() {
       
        Server.addLoggedInUser(mockUser);

        Server.removeLoggedInUser(mockUser);

   
        List<User> loggedInUsers = Server.getLoggedInUsers(); 
        assertFalse("User should be removed from the logged-in users list.", loggedInUsers.contains(mockUser));
    }

    @Test
    public void testRemoveLoggedInUserWhenNotLoggedIn() {
      
        Server.removeLoggedInUser(mockUser);
        
        try {
            Server.removeLoggedInUser(mockUser);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testNoDuplicatesInLoggedInUsers() {
    
        Server.addLoggedInUser(mockUser);
        Server.addLoggedInUser(mockUser); 

        List<User> loggedInUsers = Server.getLoggedInUsers();  
        long userCount = loggedInUsers.stream().filter(user -> user.getUsername().equals("user1")).count();

      
        assertEquals("There should only be one user with the username 'user1'.", 1, userCount);
    }

    @Test
    public void testAddUserWithNullUsername() {
        
        User nullUser = new User(null, "password", true);
        boolean added = Server.addLoggedInUser(nullUser);

        assertFalse("User with null username should not be added.", added);
    }

    @Test
    public void testAddUserWithEmptyUsername() {
       
        User emptyUser = new User("", "password", true);
        boolean added = Server.addLoggedInUser(emptyUser);

        assertFalse("User with empty username should not be added.", added);
    }

    @Test
    public void testThreadSafetyOfAddLoggedInUser() throws InterruptedException {
     
        Thread thread1 = new Thread(() -> {
            User user1 = new User("user1", "password1", true);
            Server.addLoggedInUser(user1);
        });

        Thread thread2 = new Thread(() -> {
            User user2 = new User("user1", "password2", true);  
            Server.addLoggedInUser(user2);
        });

        thread1.start();
        thread2.start();

    
        thread1.join();
        thread2.join();

        List<User> loggedInUsers = Server.getLoggedInUsers(); 
        assertEquals("Only one user should be logged in.", 1, loggedInUsers.size());
    }
      @Test
    public void testAddLoggedInUserWithNullUser() {
        // Try adding a null user to the logged-in users list
        boolean result = Server.addLoggedInUser(null);
        assertFalse("Null user should not be added to the logged-in users list.", result);
    }

    @Test
    public void testAddLoggedInUserWithInvalidUsername() {
        // Try adding a user with an invalid (e.g., empty) username
        User invalidUser = new User("", "validPassword", true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with an empty username should not be added.", result);
    }

    @Test
    public void testAddLoggedInUserWithNullUsername() {
        // Try adding a user with a null username
        User invalidUser = new User(null, "validPassword", true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with a null username should not be added.", result);
    }

    @Test
    public void testAddLoggedInUserWithNullPassword() {
        // Try adding a user with a null password
        User invalidUser = new User("newUser", null, true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with a null password should not be added.", result);
    }

    @Test
    public void testRemoveLoggedInUserWhenListIsEmpty() {
        // Attempt to remove a user from the logged-in list when no users are logged in
        Server.removeLoggedInUser(mockUser); // Should not throw any exceptions
        // No user should be removed, so no need for further checks
    }

    @Test
    public void testLoggedInUsersListIsEmptyAfterRemoval() {
        // Add a user and then remove them, checking if the list is empty
        Server.addLoggedInUser(mockUser);
        Server.removeLoggedInUser(mockUser);
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("Logged-in users list should be empty after user is removed.", loggedInUsers.isEmpty());
    }

    @Test
    public void testThreadSafetyWithMultipleUsers() throws InterruptedException {
        // Add multiple users concurrently to check thread safety
        Thread thread1 = new Thread(() -> {
            User user1 = new User("user1", "password1", true);
            Server.addLoggedInUser(user1);
        });

        Thread thread2 = new Thread(() -> {
            User user2 = new User("user2", "password2", true);
            Server.addLoggedInUser(user2);
        });

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // After both threads, there should be exactly two users in the list
        List<User> loggedInUsers = Server.getLoggedInUsers();  // Assuming a getter is available
        assertEquals("There should be two users logged in.", 2, loggedInUsers.size());
    }

    @Test
    public void testAddLoggedInUserMaxUserLimit() {
        // Simulate a maximum user count scenario
        for (int i = 0; i < 100; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

        // Adding one more user should still be successful
        User additionalUser = new User("user100", "password", true);
        boolean result = Server.addLoggedInUser(additionalUser);
        assertTrue("User should be added even with a large number of users.", result);

        // Verify the list size is 101
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertEquals("There should be 101 users logged in.", 101, loggedInUsers.size());
    }

    @Test
    public void testAddUserWhenDatabaseIsFull() {
        // Test behavior when the user database is full (simulate the edge case where no new users can be added)
        // Note: This is a conceptual test; we'd need a real limit or mechanism to test if it's full.
        // For now, we simulate this by adding a large number of users (assuming a "max users" is in place)
        // This can be adjusted based on your actual system limits (e.g., file size or memory constraints).
        for (int i = 0; i < 1000; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

        User newUser = new User("user1001", "password", true);
        boolean result = Server.addLoggedInUser(newUser);

        assertTrue("The system should handle adding users up to a certain limit.", result);

        // Ensure the user count does not exceed a predefined limit (if that limit is defined)
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("There should be no more than 1000 users logged in.", loggedInUsers.size() <= 1000);
    }

    @Test
    public void testRemoveLoggedInUserWithConcurrentAdditions() throws InterruptedException {
        // Add users concurrently and then attempt to remove one
        Thread addThread = new Thread(() -> {
            User newUser = new User("concurrentUser", "password", true);
            Server.addLoggedInUser(newUser);
        });

        Thread removeThread = new Thread(() -> {
            // Attempt to remove the user while the addition is happening
            User concurrentUser = new User("concurrentUser", "password", true);
            Server.removeLoggedInUser(concurrentUser);
        });

        addThread.start();
        removeThread.start();

        // Wait for both threads to finish
        addThread.join();
        removeThread.join();

        // Check that the user is either added or removed, based on the outcome
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("The system should handle concurrent additions and removals properly.",
                   loggedInUsers.stream().noneMatch(user -> user.getUsername().equals("concurrentUser")));
    }

    @Test
    public void testMaxLoggedInUsers() {
        // Simulate trying to add more users than a predefined limit (if implemented)
        for (int i = 0; i < 50; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

        // Try adding more than the maximum allowed (assuming the limit is 50)
        User extraUser = new User("user51", "password", true);
        boolean result = Server.addLoggedInUser(extraUser);
        assertFalse("No user should be added when the max limit of logged-in users is reached.", result);

        // Check that the size of the logged-in users list is still 50
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertEquals("The logged-in users list should not exceed the maximum limit.", 50, loggedInUsers.size());
    }
}

