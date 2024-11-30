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
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                // Add friend action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Add Friend functionality.");
            }
        });

        removeFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove friend action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Remove Friend functionality.");
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Send message action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Send Message functionality.");
            }
        });

        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Block user action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Block User functionality.");
            }
        });

        deleteMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete message action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Delete Message functionality.");
            }
        });

        searchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Search user action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "Search User functionality.");
            }
        });

        viewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // View user action logic
                JOptionPane.showMessageDialog(ClientGUI.this, "View User functionality.");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "INITIAL");
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
                    userDatabase.everythingToFile(); // Save to file after account creation
                    JOptionPane.showMessageDialog(this, "Account created successfully. Please log in.");
                    cardLayout.show(mainPanel, "LOGIN");
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists.");
                }
            } catch (BadException e) {
                JOptionPane.showMessageDialog(this, "Error creating account: " + e.getMessage());
            }
        }
    }

    private void login(String username, String password) {
        synchronized (userDatabase) {
            for (User user : userDatabase.getUsers()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // Log the user in
                    if (Server.addLoggedInUser(user)) {
                        JOptionPane.showMessageDialog(this, "Login successful.");
                        cardLayout.show(mainPanel, "MAIN_ACTIONS");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "User is already logged in.");
                        return;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Error: Invalid username or password.");
    }

    public static void main(String[] args) {
        UserDatabase userDatabase = new UserDatabase();
        new ClientGUI(userDatabase);
    }
}
