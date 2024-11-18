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
        
        assertTrue("User should be added successfully", userDatabase.addUser(user1));
        assertTrue("User should be added successfully", userDatabase.addUser(user2));

   
        assertFalse("Adding the same user should fail", userDatabase.addUser(user1));

        try {
            User invalidUser = new User("", "Password4@", true);
            assertFalse("User with invalid username should not be added", userDatabase.addUser(invalidUser));
        } catch (BadException e) {
            assertEquals("Usernames can't be empty", e.getMessage());
        }

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

     
        assertTrue("User should be removed successfully", userDatabase.removeUser(user1));
        assertFalse("User should not exist after removal", userDatabase.getUsers().contains(user1));

  
        assertFalse("Removing a non-existent user should fail", userDatabase.removeUser(user3));

        userDatabase.addUser(user3);
        assertTrue("User should be removed successfully", userDatabase.removeUser(user2));
        assertFalse("User should not exist after removal", userDatabase.getUsers().contains(user2));

        assertFalse("Trying to remove the same user again should fail", userDatabase.removeUser(user2));
    }

    @Test
    public void testSaveUser() throws IOException, BadException {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

     
        assertTrue("User data should be saved successfully", userDatabase.everythingToFile());

       
        File usersListFile = new File("UsersList.txt");
        assertTrue("UsersList.txt should exist", usersListFile.exists());

      
        if (usersListFile.exists()) {
            usersListFile.delete();
        }
    }

    @Test
    public void testEverythingToFile() throws IOException, BadException {
        userDatabase.addUser(user1);
        userDatabase.addUser(user2);

       
        assertTrue("User data should be saved successfully", userDatabase.everythingToFile());

        
        user1.sendMessage(user2, "Hello Bob!");

      
        assertTrue("User data with messages should be saved successfully", userDatabase.everythingToFile());

       
        File usersListFile = new File("UsersList.txt");
        if (usersListFile.exists()) {
            usersListFile.delete();
        }
    }

    @Test
    public void testFileCreationOnAddUser() throws IOException, BadException {
        userDatabase.addUser(user1);
        File userFile = new File("Alice.txt");

        assertTrue("User file should be created", userFile.exists());

        if (userFile.exists()) {
            userFile.delete();
        }
    }

    @Test
    public void testRemoveUserFile() throws IOException, BadException {
        userDatabase.addUser(user1);

        
        File userFile = new File("Alice.txt");
        assertTrue("User file should exist before removal", userFile.exists());

        
        userDatabase.removeUser(user1);
        assertFalse("User file should be deleted after user removal", userFile.exists());
    }

    @Test
    public void testAddMultipleUsers() throws BadException {
      
        assertTrue("User1 should be added successfully", userDatabase.addUser(user1));
        assertTrue("User2 should be added successfully", userDatabase.addUser(user2));
        assertTrue("User3 should be added successfully", userDatabase.addUser(user3));

        
        assertTrue("User1 should be in the database", userDatabase.getUsers().contains(user1));
        assertTrue("User2 should be in the database", userDatabase.getUsers().contains(user2));
        assertTrue("User3 should be in the database", userDatabase.getUsers().contains(user3));
    }

    @Test
    public void testSaveEmptyDatabase() throws IOException, BadException {
       
        assertTrue("Empty database should be saved without error", userDatabase.everythingToFile());
    }

    @Test
    public void testConcurrentAddRemoveUser() throws InterruptedException, BadException {
        
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

       
        assertFalse("User should not be present after concurrent add and remove", userDatabase.getUsers().contains(user1));
    }

    @Test
    public void testSaveUserWithNoMessages() throws IOException, BadException {
        userDatabase.addUser(user1);

     
        assertTrue("User data should be saved successfully without messages", userDatabase.everythingToFile());

        File userFile = new File("Alice.txt");
        assertTrue("User file should exist", userFile.exists());

     
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    @Test
    public void testRemoveNonExistentUser() {
       
        assertFalse("Removing a non-existent user should fail", userDatabase.removeUser(user3));
    }
    @Test
    public void testGetUserByUsername() {

        userDatabase.addUser(user1);
        userDatabase.addUser(user2);


        User result = userDatabase.getUserByUsername("Alice");
        assertNotNull("User should be found by username 'Alice'", result);
        assertEquals("Username should match", "Alice", result.getUsername());


        result = userDatabase.getUserByUsername("Charlie");
        assertNull("User should not be found by username 'Charlie'", result);


        userDatabase.removeUser(user1);
        userDatabase.removeUser(user2);
        result = userDatabase.getUserByUsername("Alice");
        assertNull("User should not be found in empty database", result);
    }

}


      

