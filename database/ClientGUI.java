import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea messageArea;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton publicRadio;
    private JRadioButton privateRadio;
    private ButtonGroup accountTypeGroup;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientGUI() {
        setupGUI();
        connectToServer("localhost", 4242);
    }

    private void setupGUI() {
        frame = new JFrame("Messaging App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initial screen
        mainPanel.add(createInitialScreen(), "INITIAL");

        // Account creation / login screen
        mainPanel.add(createAccountLoginScreen(), "ACCOUNT_LOGIN");

        // Main menu screen
        mainPanel.add(createMainMenuPanel(), "MAIN_MENU");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createInitialScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(e -> showAccountLoginScreen(true));
        createAccountButton.addActionListener(e -> showAccountLoginScreen(false));

        panel.add(loginButton);
        panel.add(createAccountButton);

        return panel;
    }

    private void showAccountLoginScreen(boolean isLogin) {
        if (isLogin) {
            // Show the login screen
            cardLayout.show(mainPanel, "ACCOUNT_LOGIN");
            ((JLabel) ((JPanel) mainPanel.getComponent(1)).getComponent(0)).setText("Login");
        } else {
            // Show the create account screen
            cardLayout.show(mainPanel, "ACCOUNT_LOGIN");
            ((JLabel) ((JPanel) mainPanel.getComponent(1)).getComponent(0)).setText("Create Account");
        }
    }

    private JPanel createAccountLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Login");
        panel.add(titleLabel);

        // Username and Password fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));

        // Account Type (only for account creation)
        JPanel accountTypePanel = new JPanel(new FlowLayout());
        publicRadio = new JRadioButton("Public");
        privateRadio = new JRadioButton("Private");
        accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(publicRadio);
        accountTypeGroup.add(privateRadio);

        accountTypePanel.add(publicRadio);
        accountTypePanel.add(privateRadio);
        panel.add(accountTypePanel);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (titleLabel.getText().equals("Login")) {
                login(username, password);
            } else {
                String accountType = publicRadio.isSelected() ? "public" : "private";
                createAccount(username, password, accountType);
            }
        });
        panel.add(submitButton);

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Main Menu
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2));
        JButton addFriendButton = new JButton("Add Friend");
        JButton removeFriendButton = new JButton("Remove Friend");
        JButton blockUserButton = new JButton("Block User");
        JButton sendMessageButton = new JButton("Send Message");
        JButton deleteMessageButton = new JButton("Delete Message");
        JButton searchUserButton = new JButton("Search User");
        JButton viewUserButton = new JButton("View User");
        JButton logoutButton = new JButton("Logout");

        addFriendButton.addActionListener(e -> addFriend());
        removeFriendButton.addActionListener(e -> removeFriend());
        blockUserButton.addActionListener(e -> blockUser());
        sendMessageButton.addActionListener(e -> sendMessage());
        deleteMessageButton.addActionListener(e -> deleteMessage());
        searchUserButton.addActionListener(e -> searchUser());
        viewUserButton.addActionListener(e -> viewUser());
        logoutButton.addActionListener(e -> logout());

        buttonPanel.add(addFriendButton);
        buttonPanel.add(removeFriendButton);
        buttonPanel.add(blockUserButton);
        buttonPanel.add(sendMessageButton);
        buttonPanel.add(deleteMessageButton);
        buttonPanel.add(searchUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(logoutButton);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private void connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            showMessage("Error: Unable to connect to server.");
        }
    }

    private void listenToServer() {
        try {
            while (true) {
                String response = (String) in.readObject();
                showMessage(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            showMessage("Connection to server lost.");
        }
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    private void login(String username, String password) {
        try {
            out.writeObject("LOGIN");
            out.writeObject(username);
            out.writeObject(password);

            String response = (String) in.readObject();
            showMessage(response);

            if (response.contains("successful")) {
                cardLayout.show(mainPanel, "MAIN_MENU");
            }
        } catch (IOException | ClassNotFoundException e) {
            showMessage("Error during login.");
        }
    }

    private void createAccount(String username, String password, String accountType) {
        try {
            out.writeObject("CREATE_ACCOUNT");
            out.writeObject(username);
            out.writeObject(password);
            out.writeObject(accountType);

            String response = (String) in.readObject();
            showMessage(response);

            if (response.contains("successfully")) {
                cardLayout.show(mainPanel, "MAIN_MENU");
            }
        } catch (IOException | ClassNotFoundException e) {
            showMessage("Error during account creation.");
        }
    }

    private void addFriend() {
        String friendUsername = JOptionPane.showInputDialog(frame, "Enter friend's username:");
        if (friendUsername != null) {
            sendAction("ADD_FRIEND", friendUsername);
        }
    }

    private void removeFriend() {
        String friendUsername = JOptionPane.showInputDialog(frame, "Enter friend's username:");
        if (friendUsername != null) {
            sendAction("REMOVE_FRIEND", friendUsername);
        }
    }

    private void blockUser() {
        String blockUsername = JOptionPane.showInputDialog(frame, "Enter username to block:");
        if (blockUsername != null) {
            sendAction("BLOCK_USER", blockUsername);
        }
    }

    private void sendMessage() {
        String recipient = JOptionPane.showInputDialog(frame, "Enter recipient's username:");
        String message = JOptionPane.showInputDialog(frame, "Enter your message:");
        if (recipient != null && message != null) {
            sendAction("SEND_MESSAGE", recipient + ":" + message);
        }
    }

    private void deleteMessage() {
        String recipient = JOptionPane.showInputDialog(frame, "Enter recipient's username:");
        String message = JOptionPane.showInputDialog(frame, "Enter message content to delete:");
        if (recipient != null && message != null) {
            sendAction("DELETE_MESSAGE", recipient + ":" + message);
        }
    }

    private void searchUser() {
        String username = JOptionPane.showInputDialog(frame, "Enter username to search:");
        if (username != null) {
            sendAction("SEARCH_USER", username);
        }
    }

    private void viewUser() {
        sendAction("VIEW_USER", null);
    }

    private void logout() {
        sendAction("LOGOUT", null);
        cardLayout.show(mainPanel, "INITIAL");
    }

    private void sendAction(String action, String data) {
        try {
            out.writeObject(action);
            out.writeObject(data);
        } catch (IOException e) {
            showMessage("Error sending action to server.");
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}
