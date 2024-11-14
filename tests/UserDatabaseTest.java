import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Team Project -- UserDatabaseTest
 *
 * Unit tests for the UserDatabase class, verifying the proper functionality of fields and methods.
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */
public class UserDatabaseTest {

    private UserDatabase userDatabase;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() throws BadException {
        userDatabase = new UserDatabase();
        user1 = new User("Alice", "Password1!", true);
        user2 = new User("Bob", "Password2@", true);
        user3 = new User("Charlie", "Password3#", true);
    }

    @Test
    public void testAddUser() {
        // Valid user addition
        assertTrue("User should be added successfully", userDatabase.addUser(user1));
        assertTrue("User should be added successfully", userDatabase.addUser(user2));

        // Attempt to add the same user twice (should fail)
        assertFalse("Adding the same user should fail", userDatabase.addUser(user1));

        // Attempt to add a user with invalid username (e.g., empty username) should also fail
        try {
            User invalidUser = new User("", "Password4@", true);
            assertFalse("User with invalid username should not be added", userDatabase.addUser(invalidUser));
        } catch (BadException e) {
            assertEquals("Usernames can't be empty", e.getMessage());
        }

        // Attempt to add a user with invalid password (should fail)
        try {
            User invalidUser = new User("Dave", "short", true);
            assertFalse("User with invalid password should not be added", userDatabase.addUser(invalidUser));
        } catch (BadException e) {
            assertEquals("You need at least eight characters, at least one uppercase letter, one digit, and one special character", e.getMessage());
        }
    }

    @Test
    public void testRemoveUser() {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

        // Valid removal of user

