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
        assertTrue("User should be removed successfully", userDatabase.removeUser(user1));
        assertFalse("User should not exist after removal", userDatabase.getUsers().contains(user1));

        // Trying to remove a user who doesn't exist
        assertFalse("Removing a non-existent user should fail", userDatabase.removeUser(user3));

        // Removing a user and verifying that the user is not in the system
        userDatabase.addUser(user3);
        assertTrue("User should be removed successfully", userDatabase.removeUser(user2));
        assertFalse("User should not exist after removal", userDatabase.getUsers().contains(user2));

        // Trying to remove the same user again
        assertFalse("Trying to remove the same user again should fail", userDatabase.removeUser(user2));
    }

    @Test
    public void testSaveUser() throws IOException, BadException {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

        // Save users to file
        assertTrue("User data should be saved successfully", userDatabase.everythingToFile());

        // Verify that the file UsersList.txt was created and contains the users
        File usersListFile = new File("UsersList.txt");
        assertTrue("UsersList.txt should exist", usersListFile.exists());

        // Clean up the file after the test
        if (usersListFile.exists()) {
            usersListFile.delete();
        }
    }

    @Test
    public void testEverythingToFile() throws IOException, BadException {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

        // Save user data to file
        assertTrue("User data should be saved successfully", userDatabase.everythingToFile());

        // Verify that user messages are saved (assuming there are none yet)
        // Here we can add messages and test saving functionality.
        user1.sendMessage(user2, "Hello Bob!");

        // Call everythingToFile again after adding messages
        assertTrue("User data with messages should be saved successfully", userDatabase.everythingToFile());

        // Clean up the file after the test
        File usersListFile = new File("UsersList.txt");
        if (usersListFile.exists()) {
            usersListFile.delete();
        }
    }

    @Test
    public void testFileCreationOnAddUser() throws IOException, BadException {
        userDatabase.addUser(user1);
        File userFile = new File("Alice.txt");

        // Check that the user file is created when a user is added
        assertTrue("User file should be created", userFile.exists());

        // Clean up the file after the test
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    @Test
    public void testRemoveUserFile() throws IOException, BadException {
        userDatabase.addUser(user1);

        // Ensure the file is created before removal
        File userFile = new File("Alice.txt");
        assertTrue("User file should exist before removal", userFile.exists());

        // Remove user and check the file is deleted
        userDatabase.removeUser(user1);
        assertFalse("User file should be deleted after user removal", userFile.exists());
    }

    @Test
    public void testAddMultipleUsers() throws BadException {
        // Adding multiple users should work
        assertTrue("User1 should be added successfully", userDatabase.addUser(user1));
        assertTrue("User2 should be added successfully", userDatabase.addUser(user2));
        assertTrue("User3 should be added successfully", userDatabase.addUser(user3));

        // Verifying all users were added
        assertTrue("User1 should be in the database", userDatabase.getUsers().contains(user1));
        assertTrue("User2 should be in the database", userDatabase.getUsers().contains(user2));
        assertTrue("User3 should be in the database", userDatabase.getUsers().contains(user3));
    }

    @Test
    public void testSaveEmptyDatabase() throws IOException, BadException {
        // Ensure saving an empty database does not cause issues
        assertTrue("Empty database should be saved without error", userDatabase.everythingToFile());
    }

    @Test
    public void testConcurrentAddRemoveUser() throws InterruptedException, BadException {
        // Testing the behavior of adding and removing users in parallel
        Thread addThread = new Thread(() -> {
            try {
                userDatabase.addUser(user1);
            } catch (BadException e) {
                e.printStackTrace();
            }
        });

        Thread removeThread = new Thread(() -> {
            userDatabase.removeUser(user1);
        });

        addThread.start();
        removeThread.start();

        addThread.join();
        removeThread.join();

        // After concurrent add and remove, the user should not be in the database
        assertFalse("User should not be present after concurrent add and remove", userDatabase.getUsers().contains(user1));
    }

    @Test
    public void testSaveUserWithNoMessages() throws IOException, BadException {
        userDatabase.addUser(user1);

        // Save users to file with no messages
        assertTrue("User data should be saved successfully without messages", userDatabase.everythingToFile());

        // Verify that the file was created for the user without messages
        File userFile = new File("Alice.txt");
        assertTrue("User file should exist", userFile.exists());

        // Clean up the file after the test
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    @Test
    public void testRemoveNonExistentUser() {
        // Trying to remove a user that does not exist should return false
        assertFalse("Removing a non-existent user should fail", userDatabase.removeUser(user3));
    }
}


      

