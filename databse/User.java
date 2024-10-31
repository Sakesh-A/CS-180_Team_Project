public class User implements UserInterface{
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<User> blockedUsers;
    private boolean privateOrPublic;

    public User(String username, String password, boolean privateOrPublic) {
        this.username = username;
        this.password = password;
        this.privateOrPublic = privateOrPublic;
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivateOrPublic() {
        return privateOrPublic;
    }

    public void setPrivateOrPublic(boolean privateOrPublic) {
        this.privateOrPublic = privateOrPublic;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }


}
