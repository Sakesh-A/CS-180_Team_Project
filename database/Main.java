import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        UserDatabase db = new UserDatabase();
        Scanner sc = new Scanner(System.in);

        System.out.println("enter your username:");
        String user = sc.nextLine();

        System.out.println("Enter your password:");
        String pass = sc.nextLine();

        while (!db.isPasswordValid(pass)) {
            System.out.println("invalid, try again");
            pass = sc.nextLine();
        }

        System.out.println("would you like to recieve messages from anybody?");
        String choice = sc.nextLine();

        boolean yOrN = false;

        if (choice.equals("y")) {
            yOrN = true;
        } else {
            yOrN = false;
        }

        if(db.saveUser(user, pass, yOrN)) {
            String filePath = "UsersList.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(a)

    }
}