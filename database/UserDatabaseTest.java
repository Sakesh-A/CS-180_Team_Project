import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class UserDatabaseTest {

    private UserDatabase userDatabase;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws BadException {
        userDatabase = new UserDatabase();
        user1 = new User("Alice", "Password1!", true);
        user2 = new User("Bob", "Password2@", true);
    }

    @Test
    public void testAddUser() {
        assertTrue("User should be added successfully", userDatabase.addUser(user1));
        assertTrue("User should be added successfully", userDatabase.addUser(user2));
        assertFalse("Adding the same user should fail", userDatabase.addUser(user1));
    }

    @Test
    public void testRemoveUser() {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

        assertTrue("User should be removed successfully", userDatabase.removeUser(user1));
        assertFalse("User should not exist after removal", userDatabase.getUsers().contains(user1));
    }

    @Test
    public void testSaveUser() throws IOException {
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
    public void testEverythingToFile() throws IOException {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);
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
}

