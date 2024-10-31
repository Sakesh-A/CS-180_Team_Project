public class User implements UserInterface{
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<User> blockedUsers;
    private boolean privateOrPublic;
}
