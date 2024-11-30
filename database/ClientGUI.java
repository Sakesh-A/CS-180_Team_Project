import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserDatabase userDatabase;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton, submitButton;
    private JLabel statusLabel;

    // Main actions buttons
    private JButton addFriendButton, removeFriendButton, sendMessageButton, blockUserButton;
    private JButton deleteMessageButton, searchUserButton, viewUserButton, logoutButton;

    private User loggedInUser = null; // Track the currently logged-in user

    public ClientGUI(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        initGUI();
    }

    private void initGUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel 1: Initial Screen with Login and Create Account buttons
        JPanel initialPanel = new JPanel();
        initialPanel.setLayout(new GridLayout(2, 1));

        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();  // Clear fields before showing login
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();  // Clear fields before showing create account
                cardLayout.show(mainPanel, "CREATE_ACCOUNT");
            }
        });

        initialPanel.add(loginButton);
        initialPanel.add(createAccountButton);

        // Panel 2: Login Screen
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        submitButton = new JButton("Submit");
        statusLabel = new JLabel(" ");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(submitButton);
        loginPanel.add(statusLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Username and password cannot be empty.");
                    return;
                }

                login(username, password);
            }
        });

        // Panel 3: Account Creation Screen
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new GridLayout(5, 1));

        JTextField createUsernameField = new JTextField();
        JPasswordField createPasswordField = new JPasswordField();
        JCheckBox publicProfileCheckbox = new JCheckBox("Public Profile?");
        JButton createAccountSubmitButton = new JButton("Create Account");
        JLabel createAccountStatusLabel = new JLabel(" ");

        createAccountPanel.add(new JLabel("Username:"));
        createAccountPanel.add(createUsernameField);
        createAccountPanel.add(new JLabel("Password:"));
        createAccountPanel.add(createPasswordField);
        createAccountPanel.add(publicProfileCheckbox);
        createAccountPanel.add(createAccountSubmitButton);
        createAccountPanel.add(createAccountStatusLabel);

        createAccountSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = createUsernameField.getText();
                String password = new String(createPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    createAccountStatusLabel.setText("Username and password cannot be empty.");
                    return;
                }

                createAccount(username, password);
            }
        });

        // Panel 4: Main Actions Screen (Post-login)
        JPanel mainActionsPanel = new JPanel();
        mainActionsPanel.setLayout(new GridLayout(5, 2));

        addFriendButton = new JButton("Add Friend");
        removeFriendButton = new JButton("Remove Friend");
        sendMessageButton = new JButton("Send Message");
        blockUserButton = new JButton("Block User");
        deleteMessageButton = new JButton("Delete Message");
        searchUserButton = new JButton("Search User");
        viewUserButton = new JButton("View User");
        logoutButton = new JButton("Logout");

        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the Add Friend screen
                cardLayout.show(mainPanel, "ADD_FRIEND");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmLogout = JOptionPane.showConfirmDialog(
                        ClientGUI.this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirmLogout == JOptionPane.YES_OPTION) {
                    loggedInUser = null; // Mark user as logged out
                    clearFields();  // Clear fields before logging out
                    cardLayout.show(mainPanel, "INITIAL");
                    JOptionPane.showMessageDialog(ClientGUI.this, "You have been logged out.");
                }
            }
        });

        mainActionsPanel.add(addFriendButton);
        mainActionsPanel.add(removeFriendButton);
        mainActionsPanel.add(sendMessageButton);
        mainActionsPanel.add(blockUserButton);
        mainActionsPanel.add(deleteMessageButton);
        mainActionsPanel.add(searchUserButton);
        mainActionsPanel.add(viewUserButton);
        mainActionsPanel.add(logoutButton);

        // Add panels to CardLayout
        mainPanel.add(initialPanel, "INITIAL");
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(createAccountPanel, "CREATE_ACCOUNT");
        mainPanel.add(mainActionsPanel, "MAIN_ACTIONS");

        // Step 5: Add Friend Panel (Search and Add Friend)
        JPanel addFriendPanel = new JPanel();
        addFriendPanel.setLayout(new GridLayout(3, 2));

        JTextField searchUsernameField = new JTextField();
        JButton searchButton = new JButton("Search");
        JLabel searchResultLabel = new JLabel(" ");
        JButton addFriendButtonInSearch = new JButton("Add Friend");

        addFriendPanel.add(new JLabel("Enter username to search:"));
        addFriendPanel.add(searchUsernameField);
        addFriendPanel.add(searchButton);
        addFriendPanel.add(searchResultLabel);
        addFriendPanel.add(addFriendButtonInSearch);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameToSearch = searchUsernameField.getText();

                if (usernameToSearch.isEmpty()) {
                    searchResultLabel.setText("Please enter a username.");
                    return;
                }

                // Search for the user in the database
                User foundUser = null;
                for (User user : userDatabase.getUsers()) {
                    if (user.getUsername().equals(usernameToSearch)) {
                        foundUser = user;
                        break;
                    }
                }

                if (foundUser != null) {
                    searchResultLabel.setText("User found: " + foundUser.getUsername());
                    // Enable the Add Friend button
                    addFriendButtonInSearch.setEnabled(true);
                } else {
                    searchResultLabel.setText("User not found.");
                    addFriendButtonInSearch.setEnabled(false);
                }
            }
        });

        addFriendButtonInSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameToSearch = searchUsernameField.getText();

                for (User user : userDatabase.getUsers()) {
                    if (user.getUsername().equals(usernameToSearch)) {
                        // Add the user as a friend
                        loggedInUser.addFriend(user);
                        JOptionPane.showMessageDialog(ClientGUI.this, "You are now friends with " + user.getUsername());
                        searchUsernameField.setText("");  // Clear only the search field after adding friend
                        searchResultLabel.setText("");    // Clear the result label
                        addFriendButtonInSearch.setEnabled(false); // Disable Add Friend button
                        cardLayout.show(mainPanel, "MAIN_ACTIONS"); // Return to main actions menu
                        return;
                    }
                }

                JOptionPane.showMessageDialog(ClientGUI.this, "User not found.");
            }
        });

        mainPanel.add(addFriendPanel, "ADD_FRIEND");

        // Set the initial view
        cardLayout.show(mainPanel, "INITIAL");

        // Frame setup
        this.setTitle("Client GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.add(mainPanel);
        this.setVisible(true);
    }

    private void createAccount(String username, String password) {
        synchronized (userDatabase) {
            try {
                User newUser = new User(username, password, true); // Assuming public profile for account creation
                if (userDatabase.addUser(newUser)) {
                    JOptionPane.showMessageDialog(this, "Account created successfully.");
                    cardLayout.show(mainPanel, "LOGIN"); // Switch to login panel after account creation
                } else {
                    JOptionPane.showMessageDialog(this, "Account creation failed.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating account.");
            }
        }
    }

    private void login(String username, String password) {
        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    if (loggedInUser == null) {
                        loggedInUser = user;
                        JOptionPane.showMessageDialog(this, "Login successful.");
                        cardLayout.show(mainPanel, "MAIN_ACTIONS");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "User already logged in.");
                        return;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        new ClientGUI(userDatabase);
    }
}
