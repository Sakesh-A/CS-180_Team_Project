import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Team Project -- ClientHandler
 *
 * A helper class for the server to handle the client. All client interactions are done here
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 * @version Nov 17, 2024
 */

class ClientHandler extends Thread implements ClientHandlerInterface {
    private Socket clientSocket;
    private final UserDatabase userDatabase;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User currentUser = null;

    public ClientHandler(Socket socket, UserDatabase userDatabase) {
        this.clientSocket = socket;
        this.userDatabase = userDatabase;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            authenticateUser(); // Ensures login or account creation before proceeding.

            while (true) {
                //sendOptions();
                String action = (String) in.readObject(); // Read client action
                System.out.println("Action requested: " + action);

                if ("LOGOUT".equalsIgnoreCase(action)) {
                    logout();
                    break;
                } else {
                    handleAction(action);
//                    sendOptions();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                if (currentUser != null) {
                    Server.removeLoggedInUser(currentUser);
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void authenticateUser() throws IOException, ClassNotFoundException {
        while (currentUser == null) {
            String action = (String) in.readObject();

            if ("LOGIN".equalsIgnoreCase(action)) {
                login();
            } else if ("CREATE_ACCOUNT".equalsIgnoreCase(action)) {
                createAccount();
            } else {
                out.writeObject("Error: Invalid action. Only LOGIN or CREATE_ACCOUNT allowed.");
            }
        }
    }


    public void handleAction(String action) throws IOException, ClassNotFoundException {
        switch (action.toUpperCase()) {
            case "ADD_FRIEND":
                addFriend();
//                sendOptions();
                break;
            case "REMOVE_FRIEND":
                removeFriend();
//                sendOptions();
                break;
            case "BLOCK_USER":
                blockUser();
//                sendOptions();
                break;
            case "SEND_MESSAGE":
                sendMessage();
//                sendOptions();
                break;
            case "DELETE_MESSAGE":
                deleteMessage();
//                sendOptions();
                break;
            case "SEARCH_USER":
                searchUser();
//                sendOptions();
                break;
            case "VIEW_USER":
                viewUser();
//                sendOptions();
                break;
            default:
                out.writeObject("Invalid action.");
//                sendOptions();
        }
    }

    public void login() throws IOException, ClassNotFoundException {
//        out.writeObject("Enter username: ");
        String username = (String) in.readObject();
//        out.writeObject("Enter password: ");
        String password = (String) in.readObject();

        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    if (Server.addLoggedInUser(user)) {
                        currentUser = user;
                        out.writeObject("Login successful.");
//                        sendOptions();
                    } else {
                        out.writeObject("Error: User is already logged in.");
                    }
                    return;
                }
            }
        }
        out.writeObject("Error: Invalid username or password.");
    }

    public void createAccount() throws IOException, ClassNotFoundException {
//        out.writeObject("Enter new username: ");
        String username = (String) in.readObject();
//        out.writeObject("Enter new password: ");
        String password = (String) in.readObject();
//        out.writeObject("Is your profile public? (true/false): ");
        boolean isPublic = Boolean.parseBoolean((String) in.readObject());

        System.out.println("Username: " + username + "  password: " + password + "  public: " + isPublic);
        synchronized (userDatabase) {
            try {
                User newUser = new User(username, password, isPublic);
                if (userDatabase.addUser(newUser)) {
                    userDatabase.everythingToFile(); // Save to file after account creation
                    out.writeObject("Account created successfully. Please log in.");
                } else {
                    out.writeObject("Error: Username already exists.");
                }
            } catch (BadException e) {
                out.writeObject("Error creating account: " + e.getMessage());
            }
        }
    }


    public void addFriend() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        // Prompt for the friend's username
        String friendUsername = (String) in.readObject();

        // Check if the friendUsername is the same as the current user's username
        if (friendUsername.equals(currentUser.getUsername())) {
            out.writeObject("You cannot add yourself as a friend.");
            return;
        }

        synchronized (userDatabase) {
            boolean userFound = false;

            // Iterate through the users in the database
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(friendUsername)) {
                    userFound = true; // Found the user

                    // Try adding the friend
                    if (currentUser.addFriend(user)) {
                        userDatabase.everythingToFile(); // Save after adding friend
                        out.writeObject("Friend added successfully.");
                    } else {
                        out.writeObject("User is already in your friend list.");
                    }
                    break;
                }
            }

            // If no user was found, notify the client
            if (!userFound) {
                out.writeObject("User not found.");
            }
        }
    }

    public void removeFriend() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        out.writeObject("Enter the username of the friend to remove: ");
        String friendUsername = (String) in.readObject();

        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(friendUsername)) {
                    if (currentUser.removeFriend(user)) {
                        userDatabase.everythingToFile(); // Save to file after modifying friends list
                        out.writeObject("Friend removed successfully.");
                    } else {
                        out.writeObject("User is not in your friend list.");
                    }
                    return;
                }
            }
        }
        out.writeObject("User not found.");
    }

    public void blockUser() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        out.writeObject("Enter the username of the user to block: ");
        String blockUsername = (String) in.readObject();

        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(blockUsername)) {
                    if (currentUser.blockUser(user)) {
                        userDatabase.everythingToFile(); // Save to file after blocking a user
                        out.writeObject("User blocked successfully.");
                    } else {
                        out.writeObject("User is already blocked.");
                    }
                    return;
                }
            }
        }
        out.writeObject("User not found.");
    }

    public void sendMessage() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        out.writeObject("Enter the username of the recipient: ");
        String recipientUsername = (String) in.readObject();
        out.writeObject("Enter your message: ");
        String messageContent = (String) in.readObject();

        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(recipientUsername)) {
                    if (currentUser.sendMessage(user, messageContent)) {
                        userDatabase.everythingToFile(); // Save to file after sending a message
                        out.writeObject("Message sent successfully.");
                    } else {
                        out.writeObject("Failed to send message."
                                       + "Check your permissions or ensure the user has not blocked you.");
                    }
                    return;
                }
            }
        }
        out.writeObject("User not found.");
    }

    public void deleteMessage() throws IOException, ClassNotFoundException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        out.writeObject("Enter the username of the message recipient to delete: ");
        String recipientUsername = (String) in.readObject();
        out.writeObject("Enter the message content to delete: ");
        String messageContent = (String) in.readObject();

        synchronized (userDatabase) {
            for (TextMessage message : currentUser.getMessages()) {
                if (message.getReceiverUsername().equals(recipientUsername) &&
                        message.getMessageContent().equals(messageContent)) {
                    if (currentUser.deleteMessage(message)) {
                        userDatabase.everythingToFile(); // Save to file after deleting a message
                        out.writeObject("Message deleted successfully.");
                    } else {
                        out.writeObject("Failed to delete message.");
                    }
                    return;
                }
            }
        }
        out.writeObject("Message not found.");
    }

    public void searchUser() throws IOException, ClassNotFoundException {
        out.writeObject("Enter the username to search for: ");
        String username = (String) in.readObject();

        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(username)) {
                    out.writeObject("User found: " + user.getUsername());
                    return;
                }
            }
        }
        out.writeObject("User not found.");
    }

    public void viewUser() throws IOException {
        if (currentUser == null) {
            out.writeObject("You must log in first.");
            return;
        }

        out.writeObject("Viewing your information: " + currentUser);
    }

    public void logout() throws IOException {
        if (currentUser != null) {
            Server.removeLoggedInUser(currentUser);
            userDatabase.everythingToFile(); // Save to file after logging out
            currentUser = null;
        }
        out.writeObject("You have logged out.");
    }

//    private void sendOptions() throws IOException {
//        out.writeObject("Available actions: \n" +
//                "1. ADD_FRIEND\n" +
//                "2. REMOVE_FRIEND\n" +
//                "3. BLOCK_USER\n" +
//                "4. SEND_MESSAGE\n" +
//                "5. DELETE_MESSAGE\n" +
//                "6. SEARCH_USER\n" +
//                "7. VIEW_USER\n" +
//                "8. LOGOUT\n" +
//                "Enter your choice:");
//    }
/*    private void sendOptions() throws IOException {
        out.writeObject("Available actions: 1. ADD_FRIEND, 2. REMOVE_FRIEND, 3. BLOCK_USER, 
        4. SEND_MESSAGE, 5. DELETE_MESSAGE, 6. SEARCH_USER, 7. VIEW_USER, 8.LOGOUT");
    }*/

}
