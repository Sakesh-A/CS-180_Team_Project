public interface UserInterface {
    public void sendMessage(User u);
    public void receiveMessage();
    public void deleteMessage(TextMessage m);
    public void deleteMessage(PhotoMessage m);
    public void addFriend(User u);
    public void removeFriend(User u);
    public void blockUser(User u);
}
