public interface UserInterface {
    public void sendMessage(User u);
    public void receiveMessage();
    public void deleteMessage(TextMessage m);
    public void deleteMessage(PhotoMessage m);
    public boolean addFriend(User u);
    public boolean removeFriend(User u);
    public boolean blockUser(User u);
}
