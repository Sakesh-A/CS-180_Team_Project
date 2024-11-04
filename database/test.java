public class test {
    public static void main(String []args) {
        try {
            User u1 = new User("John", "j#idsfHO44", true);
            User u2 = new User("Bob", "jj*&OGYLKJL!@@@112j", true);
            User u3 = new User("Bob", "j#idsfHO44", true);
            UserDatabase db = new UserDatabase();
            db.addUser(u1);
            db.addUser(u2);
            db.addUser(u3);
            u1.addFriend(u2);

            u1.sendMessage(u2, "hello 1");
            u1.sendMessage(u2, "hello 2");
            u1.deleteMessage(u1.getMessages().get(0));
            u1.addFriend(u3);
            db.removeUser(u3);
            //System.out.println(u1.toString());
            db.everythingToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
