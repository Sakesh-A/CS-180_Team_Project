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
}

