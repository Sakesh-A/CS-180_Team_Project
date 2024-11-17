import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * Team Project -- ServerTest
 *
 * Tests for the Server Class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public class ServerTest {

    private User mockUser;

    @Before
    public void setUp() {

        try {

            mockUser = new User("user1", "Password1!", true); // Username, password, and public profile
        }
        catch(BadException e){
            assertEquals("Mock user can't be created.", e.getMessage());
        }

    }


    @Test
    public void testAddLoggedInUser() {
        boolean result = Server.addLoggedInUser(mockUser);
        assertTrue("User should be added to the logged-in users list.", result);
    }


    @Test
    public void testAddLoggedInUserTwice() {
        Server.addLoggedInUser(mockUser);
        boolean result = Server.addLoggedInUser(mockUser);
        assertFalse("User should not be added if already logged in.", result);
    }






    @Test
    public void testAddLoggedInUserWithNullUser() {
        boolean result = Server.addLoggedInUser(null);
        assertFalse("Null user should not be added to the logged-in users list.", result);
    }

    @Test
    public void testRemoveLoggedInUserWhenListIsEmpty() {
        Server.removeLoggedInUser(mockUser);
    }

    @Test
    public void testLoggedInUsersListIsEmptyAfterRemoval() {
        Server.addLoggedInUser(mockUser);
        Server.removeLoggedInUser(mockUser);

    }


    @Test
    public void testServerAddUserWithBadException() {
        try {
            User invalidUser = new User(null, "password", true);
            boolean result = Server.addLoggedInUser(invalidUser);
            assertFalse("User should not be added with null username.", result);
        } catch (BadException e) {
            assertEquals("Usernames can't be empty", e.getMessage());
        }
    }


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


    @Test
    public void testBadExceptionHandling() {
        try {
            throw new BadException("This is a bad exception");
        } catch (BadException e) {
            assertEquals("This is a bad exception", e.getMessage());
        }
    }
}
