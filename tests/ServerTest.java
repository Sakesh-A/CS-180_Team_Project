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
       
        boolean result = Server.addLoggedInUser(null);
        assertFalse("Null user should not be added to the logged-in users list.", result);
    }

    @Test
    public void testAddLoggedInUserWithInvalidUsername() {
        
        User invalidUser = new User("", "validPassword", true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with an empty username should not be added.", result);
    }

    @Test
    public void testAddLoggedInUserWithNullUsername() {
        
        User invalidUser = new User(null, "validPassword", true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with a null username should not be added.", result);
    }

    @Test
    public void testAddLoggedInUserWithNullPassword() {
    
        User invalidUser = new User("newUser", null, true);
        boolean result = Server.addLoggedInUser(invalidUser);
        assertFalse("User with a null password should not be added.", result);
    }

    @Test
    public void testRemoveLoggedInUserWhenListIsEmpty() {
      
        Server.removeLoggedInUser(mockUser); // Should not throw any exceptions
       
    }

    @Test
    public void testLoggedInUsersListIsEmptyAfterRemoval() {
       
        Server.addLoggedInUser(mockUser);
        Server.removeLoggedInUser(mockUser);
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("Logged-in users list should be empty after user is removed.", loggedInUsers.isEmpty());
    }

    @Test
    public void testThreadSafetyWithMultipleUsers() throws InterruptedException {
      
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

       
        thread1.join();
        thread2.join();

        List<User> loggedInUsers = Server.getLoggedInUsers();  
        assertEquals("There should be two users logged in.", 2, loggedInUsers.size());
    }

    @Test
    public void testAddLoggedInUserMaxUserLimit() {
        
        for (int i = 0; i < 100; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

        User additionalUser = new User("user100", "password", true);
        boolean result = Server.addLoggedInUser(additionalUser);
        assertTrue("User should be added even with a large number of users.", result);

     
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertEquals("There should be 101 users logged in.", 101, loggedInUsers.size());
    }

    @Test
    public void testAddUserWhenDatabaseIsFull() {
     
        for (int i = 0; i < 1000; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

        User newUser = new User("user1001", "password", true);
        boolean result = Server.addLoggedInUser(newUser);

        assertTrue("The system should handle adding users up to a certain limit.", result);

        
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("There should be no more than 1000 users logged in.", loggedInUsers.size() <= 1000);
    }

    @Test
    public void testRemoveLoggedInUserWithConcurrentAdditions() throws InterruptedException {
     
        Thread addThread = new Thread(() -> {
            User newUser = new User("concurrentUser", "password", true);
            Server.addLoggedInUser(newUser);
        });

        Thread removeThread = new Thread(() -> {
            
            User concurrentUser = new User("concurrentUser", "password", true);
            Server.removeLoggedInUser(concurrentUser);
        });

        addThread.start();
        removeThread.start();

        addThread.join();
        removeThread.join();

        
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertTrue("The system should handle concurrent additions and removals properly.",
                   loggedInUsers.stream().noneMatch(user -> user.getUsername().equals("concurrentUser")));
    }

    @Test
    public void testMaxLoggedInUsers() {
       
        for (int i = 0; i < 50; i++) {
            User user = new User("user" + i, "password", true);
            Server.addLoggedInUser(user);
        }

  
        User extraUser = new User("user51", "password", true);
        boolean result = Server.addLoggedInUser(extraUser);
        assertFalse("No user should be added when the max limit of logged-in users is reached.", result);

       
        List<User> loggedInUsers = Server.getLoggedInUsers();
        assertEquals("The logged-in users list should not exceed the maximum limit.", 50, loggedInUsers.size());
    }
}

