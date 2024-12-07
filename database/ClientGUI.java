import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String currentUser;

    public ClientGUI(Socket socket) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Messaging App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all panels to the main panel

        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createAccountPanel(), "CreateAccount");
        mainPanel.add(createActionPanel(), "ActionMenu");

        // Placeholder panels for individual actions
        String[] actions = {"AddFriend", "RemoveFriend", "BlockUser", "SendMessage", "DeleteMessage", "SearchUser", "ViewUser", "ViewMessages"};
        for (String action : actions) {
            mainPanel.add(createActionPlaceholder(action), action);
        }

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu"); // Start at the main menu

        setVisible(true);
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public InputStream getIn() {
        return in;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to Messaging App", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(welcomeLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        panel.add(loginButton);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));
        panel.add(createAccountButton);

        return panel;
    }

    public JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel loginLabel = new JLabel("Login", SwingConstants.CENTER);
        panel.add(loginLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            try {
                out.writeObject("LOGIN");
                out.writeObject(usernameField.getText());
                out.writeObject(new String(passwordField.getPassword()));

                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response);

                if (response.contains("Login successful")) {
                    currentUser = usernameField.getText();
                    usernameField.setText(""); // Reset fields
                    passwordField.setText("");
                    cardLayout.show(mainPanel, "ActionMenu");
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);

        return panel;
    }

    public JPanel createAccountPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel createAccountLabel = new JLabel("Create Account", SwingConstants.CENTER);
        panel.add(createAccountLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JCheckBox publicCheckBox = new JCheckBox("Public Profile");
        panel.add(publicCheckBox);

        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> {
            try {
                // Send account creation request to the server
                out.writeObject("CREATE_ACCOUNT");
                out.writeObject(usernameField.getText());
                out.writeObject(new String(passwordField.getPassword()));
                out.writeObject(publicCheckBox.isSelected() ? "true" : "false");
                out.flush(); // Ensure data is sent immediately

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                // Navigate back to Main Menu on success
                if (response.startsWith("Account created successfully")) {
                    usernameField.setText(""); // Clear inputs for the next user
                    passwordField.setText("");
                    publicCheckBox.setSelected(false);
                    cardLayout.show(mainPanel, "MainMenu");
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Could not connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        buttonPanel.add(createButton);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));


        String[] actions = {"AddFriend", "RemoveFriend", "BlockUser", "SendMessage", "DeleteMessage", "SearchUser", "ViewUser", "ViewMessages"};
        for (String action : actions) {
            panel.add(createActionButton(action.replaceAll("([a-z])([A-Z])", "$1 $2"), action));
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            try {
                out.writeObject("LOGOUT");
                currentUser = null;
                //cardLayout.show(mainPanel, "MainMenu");
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(logoutButton);

        return panel;
    }

    public JButton createActionButton(String label, String actionPanel) {
        JButton button = new JButton(label);
        button.addActionListener(e -> cardLayout.show(mainPanel, actionPanel));
        return button;
    }

    public JPanel createActionPlaceholder(String actionName) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(actionName + " Page", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        if (actionName.equals("AddFriend")) {
            panel.add(createAddFriendPanel(), BorderLayout.CENTER);
        } else if (actionName.equals("RemoveFriend")) {
            panel.add(createRemoveFriendPanel(), BorderLayout.CENTER);
        } else if (actionName.equals("BlockUser")) {
            panel.add(createBlockUserPanel(), BorderLayout.CENTER);
        } else if (actionName.equals("SendMessage")) {
            panel.add(createSendMessagePanel(), BorderLayout.CENTER);
        } else if (actionName.equals("DeleteMessage")) {
            panel.add(createDeleteMessagePanel(), BorderLayout.CENTER);
        } else if (actionName.equals("SearchUser")) {
            panel.add(createSearchUserPanel(), BorderLayout.CENTER);
        } else if (actionName.equals("ViewUser")) {
            panel.add(createViewUserPanel(), BorderLayout.CENTER);
        } else if (actionName.equals("ViewMessages")) {
            panel.add(createMessagesPanel(), BorderLayout.CENTER);
        }

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            try {
                out.writeObject("LOGOUT");
                currentUser = null;
                System.exit(0);
                //cardLayout.show(mainPanel, "MainMenu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createAddFriendPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Add Friend", SwingConstants.CENTER);
        panel.add(label);

        JTextField friendUsernameField = new JTextField();
        friendUsernameField.setBorder(BorderFactory.createTitledBorder("Friend's Username"));
        panel.add(friendUsernameField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Friend");
        addButton.addActionListener(e -> {
            try {
                String friendUsername = friendUsernameField.getText();
                if (friendUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a friend's username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (friendUsername.equals(currentUser)) {
                    JOptionPane.showMessageDialog(this, "You cannot add yourself as a friend.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the add friend request to the server
                out.writeObject("ADD_FRIEND"); // Current user
                out.writeObject(friendUsername);  // The friend to be added
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                if (response.equals("Friend added successfully")) {
                    friendUsernameField.setText(""); // Clear input after success
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }




    public JPanel createRemoveFriendPanel() {

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Remove Friend", SwingConstants.CENTER);
        panel.add(label);

        JTextField friendUsernameField = new JTextField();
        friendUsernameField.setBorder(BorderFactory.createTitledBorder("Friend's Username"));
        panel.add(friendUsernameField);

        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Remove Friend");
        removeButton.addActionListener(e -> {
            try {
                String friendUsername = friendUsernameField.getText();
                if (friendUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a friend's username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (friendUsername.equals(currentUser)) {
                    JOptionPane.showMessageDialog(this, "You cannot remove yourself as a friend.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the remove friend request to the server
                out.writeObject("REMOVE_FRIEND"); // Current user
                out.writeObject(friendUsername);  // The friend to be removed
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                if (response.equals("Friend removed successfully")) {
                    friendUsernameField.setText(""); // Clear input after success
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }

    // Add a Block User panel
    public JPanel createBlockUserPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Block User", SwingConstants.CENTER);
        panel.add(label);

        JTextField friendUsernameField = new JTextField();
        friendUsernameField.setBorder(BorderFactory.createTitledBorder("Username to Block"));
        panel.add(friendUsernameField);

        JPanel buttonPanel = new JPanel();
        JButton blockButton = new JButton("Block User");
        blockButton.addActionListener(e -> {
            try {
                String friendUsername = friendUsernameField.getText();
                if (friendUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a friend's username to block.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (friendUsername.equals(currentUser)) {
                    JOptionPane.showMessageDialog(this, "You cannot block yourself.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the block request to the server
                out.writeObject("BLOCK_USER");
                out.writeObject(friendUsername);
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                if (response.equals("User blocked successfully")) {
                    friendUsernameField.setText(""); // Clear input after success
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(blockButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }

    public JPanel createSendMessagePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("Send Message", SwingConstants.CENTER);
        panel.add(label);

        JTextField recipientUsernameField = new JTextField();
        recipientUsernameField.setBorder(BorderFactory.createTitledBorder("Recipient's Username"));
        panel.add(recipientUsernameField);

        JTextArea messageTextArea = new JTextArea();
        messageTextArea.setBorder(BorderFactory.createTitledBorder("Message"));
        panel.add(new JScrollPane(messageTextArea));

        JPanel buttonPanel = new JPanel();
        JButton sendButton = new JButton("Send Message");
        sendButton.addActionListener(e -> {
            try {
                String recipientUsername = recipientUsernameField.getText();
                String message = messageTextArea.getText();

                if (recipientUsername.isEmpty() || message.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both recipient's username and a message.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (recipientUsername.equals(currentUser)) {
                    JOptionPane.showMessageDialog(this, "You cannot send a message to yourself.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the send message request to the server
                out.writeObject("SEND_MESSAGE");
                out.writeObject(recipientUsername);  // The recipient's username
                out.writeObject(message);  // The message
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                if (response.equals("Message sent successfully")) {
                    recipientUsernameField.setText(""); // Clear input after success
                    messageTextArea.setText(""); // Clear message area
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(sendButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }

    public JPanel createDeleteMessagePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("Delete Message", SwingConstants.CENTER);
        panel.add(label);

        JTextField recipientUsernameField = new JTextField();
        recipientUsernameField.setBorder(BorderFactory.createTitledBorder("Recipient's Username"));
        panel.add(recipientUsernameField);

        JTextField messageContentField = new JTextField();
        messageContentField.setBorder(BorderFactory.createTitledBorder("Message Content"));
        panel.add(messageContentField);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Message");
        deleteButton.addActionListener(e -> {
            try {
                String recipientUsername = recipientUsernameField.getText();
                String messageContent = messageContentField.getText();

                if (recipientUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the recipient's username.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (messageContent.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the message content.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the delete message request to the server
                out.writeObject("DELETE_MESSAGE");
                out.writeObject(recipientUsername); // Send recipient username
                out.writeObject(messageContent);   // Send message content
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Info", JOptionPane.INFORMATION_MESSAGE);

                if (response.equals("Message deleted successfully")) {
                    recipientUsernameField.setText(""); // Clear input after success
                    messageContentField.setText("");
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }

    public JPanel createSearchUserPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Search User", SwingConstants.CENTER);
        panel.add(label);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Enter Username to Search"));
        panel.add(usernameField);

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a username to search.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Send the search request to the server
                out.writeObject("SEARCH_USER");
                out.writeObject(username); // Send the username to search
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Search Result", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ActionMenu"));
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

        return panel;
    }

    public JPanel createViewUserPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        JLabel label = new JLabel("View Profile Info", SwingConstants.CENTER);
        panel.add(label);

        JPanel buttonPanel = new JPanel();
        JButton viewButton = new JButton("View My Info");
        viewButton.addActionListener(e -> {
            try {
                // Send the view user request to the server
                out.writeObject("VIEW_USER");
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Your Information", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(viewButton);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createMessagesPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        JLabel label = new JLabel("Viewing your messages", SwingConstants.CENTER);
        panel.add(label);

        JPanel buttonPanel = new JPanel();
        JButton viewButton = new JButton("View My Messages");
        viewButton.addActionListener(e -> {
            try {
                // Send the view user request to the server
                out.writeObject("VIEW_MESSAGES");
                out.flush();

                // Read the server's response
                String response = (String) in.readObject();
                JOptionPane.showMessageDialog(this, response, "Your Messages", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(viewButton);

        panel.add(buttonPanel);

        return panel;


    }


    public static void main(String[] args) {
        // Specify the server's IP address and port number
        String serverAddress = "localhost"; // or the IP address of your server
        int port = 4242; // Use the appropriate port number

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(serverAddress, port);

            // Create the ClientGUI and pass the socket
            new ClientGUI(socket);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
