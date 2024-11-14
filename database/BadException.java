/**
 * Team Project -- BadException
 *
 * Custom exception class for handling specific error conditions in the project.
 * This exception can be used to provide descriptive error messages when certain 
 * conditions are not met.
 * 
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 3, 2024
 */

public class BadException extends Exception {

    /**
     * Constructs a BadException with a specified detail message.
     *
     * @param message the detail message, which provides additional information about the exception
     */
    public BadException(String message) {
        super(message);
    }

}
