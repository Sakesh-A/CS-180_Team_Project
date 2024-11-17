import java.util.ArrayList;
import java.util.List;

/**
 * Team Project -- ServerInterface
 *
 * An interface for our server
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

public interface ServerInterface {
    static final int PORT = 12345;
    static UserDatabase userDatabase = new UserDatabase();
    static final List<User> loggedInUsers = new ArrayList<>();
    static final Object loginLock = new Object();
    public static boolean addLoggedInUser(User user) {

        synchronized (loginLock) {
            for (User loggedInUser : loggedInUsers) {
                if (loggedInUser.getUsername().equals(user.getUsername())) {
                    return false; // User is already logged in
                }
            }
            loggedInUsers.add(user);
            return true;
        }
    }

    /**
     * Removes a user from the list of logged-in users, ensuring thread safety.
     */
    public static void removeLoggedInUser(User user) {
        synchronized (loginLock) {
            loggedInUsers.remove(user);
        }
    }
}
