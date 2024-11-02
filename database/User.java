import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class User implements UserInterface {
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<User> blockedUsers;
    private boolean isPublic;
    private ArrayList<TextMessage> messages; // we needed a way to differentiate between recievers, so we are doing arrays
    private ArrayList<PhotoMessage> photos;

    public User(String username, String password, boolean isPublic) throws BadException {

        for (char c : username.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '_')) {
                throw new BadException("Usernames can only contain letters, numbers, or underscores.");
            }
        }
        if (username == null || username.length() > 20 || username.contains(" ")) {
            throw new BadException("Username cannot contain more than 20 characters, have a space, or be empty.");
        }
        this.username = username;


        if (password.length() < 8) {
            throw new BadException("You need at least eight characters");
        }

        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        if (!hasUpperCase || !hasDigit || !hasSpecialCharacter) {
            throw new BadException("You need at least eight characters, at least one uppercase letter, one digit, and one ");
        }
        this.password = password;
        this.isPublic = isPublic;
        this.friends = new ArrayList<User>();
        this.blockedUsers = new ArrayList<User>();
        this.messages = new ArrayList<TextMessage>();
        this.photos = new ArrayList<PhotoMessage>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
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

    public boolean addFriend(User u) {
        boolean exists = false;
        try (BufferedReader bfr = new BufferedReader(new FileReader("UsersList.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(u.getUsername())) {
                    exists = true;
                }
            }
        } catch(IOException e) {
            return false;
        }
        if (!exists) {return false;}
        for(User user : friends){
            if(user.equals(u)) {
                return false;
            }
        }
        friends.add(u);
        return true;
    }
    public ArrayList<TextMessage> getMessages() {
        return messages;
    }

    public boolean removeFriend(User u) {
        boolean exists = false;
        for (User a : this.friends) {
            if (a.equals(u)) {
                exists = true;
            }
        }
        if (exists) {
            friends.remove(u);
            return true;
        }
        return false;
    }

    public boolean blockUser(User u) {
        boolean exists = false;
        try (BufferedReader bfr = new BufferedReader(new FileReader("UsersList.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(u.getUsername())) {
                    exists = true;
                }
            }
        } catch(IOException e) {
            return false;
        }
        if (!exists) {return false;}
        for(User user : blockedUsers){
            if(user.equals(u)) {
                return false;
            }
        }
        blockedUsers.add(u);
        return true;


    }

    public boolean sendMessage(User person, String message) {
        if (person.hasBlocked(this) || (person.isPublic && !person.hasFriended(this))) {
            return false;
        }
        TextMessage m = new TextMessage(message, this, person);
        this.messages.add(m);
        person.messages.add(m);
//        TextMessage.id++;
        return true;
    }
    public boolean hasBlocked(User u) {
        for (int i = 0; i < blockedUsers.size(); i++) {
            if (blockedUsers.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasFriended(User u) {
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).equals(u)) {
                return true;
            }
        }
        return false;
    }

    public boolean sendPhotoMessage(User person, String message, String photo) {
        if (person.hasBlocked(this) || (person.isPublic && !person.hasFriended(this))) {
            return false;
        }
        PhotoMessage m = new PhotoMessage(message, this, person, photo);
        this.photos.add(m);
        person.photos.add(m);
//        TextMessage.id++;
        return true;
    }

    public void deleteMessage(TextMessage message) {
        for (int i = 0; i < this.messages.size(); i++) {
            if (this.messages.get(i).equals(message)) {
                this.messages.remove(i);
                break;
            }
        }
        for (int i = 0; i < message.getReceiver().messages.size(); i++) {
            if (message.getReceiver().messages.get(i).equals(message)) {
                message.getReceiver().messages.remove(i);
                break;
            }
        }
    }

    public void deletePhotoMessage(PhotoMessage photo) {
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).equals(photo)) {
                photos.remove(i);
                break;
            }
        }
        for (int i = 0; i < photo.getReceiver().photos.size(); i++) {
            if (photo.getReceiver().photos.get(i).equals(photo)) {
                photo.getReceiver().photos.remove(i);
                break;
            }
        }
    }

    public boolean equals(Object o){
        if(o instanceof User){
            User u = (User) o;
          if(u.password.equals(this.password) && u.username.equals(this.username)) {
                 return true;
          }
          return false;

        }
        return false;

    }


}
