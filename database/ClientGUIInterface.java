import javax.swing.*;

public interface ClientGUIInterface {
    public JPanel createAccountPanel();
    public JPanel createMainMenuPanel();
    public JPanel createActionPanel();
    public JButton createActionButton(String label, String actionPanel);
    public JPanel createActionPlaceholder(String actionName);
    public JPanel createAddFriendPanel();
    public JPanel createRemoveFriendPanel();
    public JPanel createBlockUserPanel();
    public JPanel createSendMessagePanel();
    public JPanel createDeleteMessagePanel();
    public JPanel createSearchUserPanel();
    public JPanel createViewUserPanel();
    public JPanel createMessagesPanel();

}
