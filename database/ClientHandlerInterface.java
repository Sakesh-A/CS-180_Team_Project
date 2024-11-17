import java.io.IOException;

public interface ClientHandlerInterface {
    public void authenticateUser() throws IOException, ClassNotFoundException;
    public void handleAction(String action) throws IOException, ClassNotFoundException;
    public void login() throws IOException, ClassNotFoundException;
    public void createAccount() throws IOException, ClassNotFoundException;
    public void addFriend() throws IOException, ClassNotFoundException;
    public void removeFriend() throws IOException, ClassNotFoundException;
    public void blockUser() throws IOException, ClassNotFoundException;
    public void sendMessage() throws IOException, ClassNotFoundException;
    public void deleteMessage() throws IOException, ClassNotFoundException;
    public void searchUser() throws IOException, ClassNotFoundException;
    public void viewUser() throws IOException;
    public void logout() throws IOException;




}
