import java.io.IOException;
import java.util.ArrayList;

/**
 * Team Project -- UserDatabaseInterface
 *
 * Defines an interface for user database operations, including methods for adding, removing, 
 * and saving user data to a file.
 * 
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */

public interface UserDatabaseInterface {

    /**
     * Retrieves the list of all users in the database.
     * 
     * @return an ArrayList of User objects
     */
    ArrayList<User> getUsers();

    /**
     * Sets the list of users in the database.
     * 
     * @param users an ArrayList of User objects to set as the database's user list
     */
    void setUsers(ArrayList<User> users);

    /**
     * Adds a user to the database.
     * 
     * @param user the User object to add
     * @return true if the user was successfully added; false otherwise
     * @throws IOException if an I/O error occurs while adding the user
     */
    boolean addUser(User user) throws IOException;

    /**
     * Removes a user from the database.
     * 
     * @param user the User object to remove
     * @return true if the user was successfully removed; false otherwise
     * @throws IOException if an I/O error occurs while removing the user
     */
    boolean removeUser(User user) throws IOException;

    /**
     * Writes all user data to a file.
     * 
     * @return true if the data was successfully written to the file; false otherwise
     * @throws IOException if an I/O error occurs during the file operation
     */
    boolean everythingToFile() throws IOException;

    /**
     * Closes any open writers associated with the database, releasing resources.
     */
//    void closeAllWriters();
}
