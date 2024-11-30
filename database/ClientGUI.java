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
    private JTextField friendField;
    private JTextField messageField;

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

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMainMenuPanel(), "MAIN_MENU");
        frame.add(mainPanel);

        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(e -> login());
        createAccountButton.addActionListener(e -> createAccount());

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(createAccountButton);

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

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

    private void login() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

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

    private void createAccount() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            out.writeObject("CREATE_ACCOUNT");
            out.writeObject(username);
            out.writeObject(password);

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
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void sendAction(String action, String data) {
        try {
            out.writeObject(action);
            if (data != null) {
                out.writeObject(data);
            }
        } catch (IOException e) {
            showMessage("Error sending action: " + action);
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}
