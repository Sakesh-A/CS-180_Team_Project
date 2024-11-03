import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new FileReader("input.txt"))) {
            String[] files = inputReader.readLine().split(" ");
            if (files.length < 1) {
                System.out.println("Insufficient commands in input.txt");
                return;
            }

            UserDatabase db = new UserDatabase();

            try (BufferedWriter mainOutWriter = new BufferedWriter(new FileWriter("output.txt"))) {
                mainOutWriter.write("User Database Started");
                mainOutWriter.newLine();

                String commandLine;

                while ((commandLine = inputReader.readLine()) != null) {
                    boolean result = false;

                    try {
                        int command = Integer.parseInt(commandLine);
                        switch (command) {
                            case 1:
                                String username1 = inputReader.readLine();
                                String password1 = inputReader.readLine();
                                boolean isPublic1 = Boolean.parseBoolean(inputReader.readLine());
                                if (username1 != null && password1 != null) {
                                    result = db.addUser(new User(username1, password1, isPublic1));
                                }
                                break;
                            case 2:
                                String username2 = inputReader.readLine();
                                String password2 = inputReader.readLine();
                                boolean isPublic2 = Boolean.parseBoolean(inputReader.readLine());
                                if (username2 != null && password2 != null) {
                                    result = db.removeUser(new User(username2, password2, isPublic2));
                                }
                                break;
                            case 3:
                                String username3 = inputReader.readLine();
                                String password3 = inputReader.readLine();
                                boolean isPublic3 = Boolean.parseBoolean(inputReader.readLine());
                                String username4 = inputReader.readLine();
                                String password4 = inputReader.readLine();
                                boolean isPublic4 = Boolean.parseBoolean(inputReader.readLine());
                                User user1= new User(username3, password3, isPublic3);
                                User user2= new User(username4, password4, isPublic4);
                                if (username3 != null && password3 != null && username4 != null && password4 != null) {
                                    int first = 0;
                                    int second = 0;
                                    for (int i = 0; i < db.getUsers().size(); i++) {
                                        if (user1.equals(db.getUsers().get(i))) {
                                            first = i;
                                        }
                                        if (user2.equals(db.getUsers().get(i))) {
                                            second = i;
                                        }
                                    }
                                    result = db.getUsers().get(first).addFriend(db.getUsers().get(second));
                                }
                                break;
                            case 4:
                                String username5 = inputReader.readLine();
                                String password5 = inputReader.readLine();
                                boolean isPublic5 = Boolean.parseBoolean(inputReader.readLine());
                                String username6 = inputReader.readLine();
                                String password6 = inputReader.readLine();
                                boolean isPublic6 = Boolean.parseBoolean(inputReader.readLine());
                                User user3 = new User(username5, password5, isPublic5);
                                User user4 = new User(username6, password6, isPublic6);
                                if (username5 != null && password5 != null && username6 != null && password6 != null) {
                                    int first = 0;
                                    int second = 0;
                                    for (int i = 0; i < db.getUsers().size(); i++) {
                                        if (user3.equals(db.getUsers().get(i))) {
                                            first = i;
                                        }
                                        if (user4.equals(db.getUsers().get(i))) {
                                            second = i;
                                        }
                                    }
                                    result = db.getUsers().get(first).removeFriend(db.getUsers().get(second));
                                }
                                break;
                            case 5:
                                String username7 = inputReader.readLine();
                                String password7 = inputReader.readLine();
                                boolean isPublic7 = Boolean.parseBoolean(inputReader.readLine());
                                String username8 = inputReader.readLine();
                                String password8 = inputReader.readLine();
                                boolean isPublic8 = Boolean.parseBoolean(inputReader.readLine());
                                User user5 = new User(username7, password7, isPublic7);
                                User user6 = new User(username8, password8, isPublic8);
                                if (username7 != null && password7 != null && username8 != null && password8 != null) {
                                    int first = 0;
                                    int second = 0;
                                    for (int i = 0; i < db.getUsers().size(); i++) {
                                        if (user5.equals(db.getUsers().get(i))) {
                                            first = i;
                                        }
                                        if (user6.equals(db.getUsers().get(i))) {
                                            second = i;
                                        }
                                    }
                                    result = db.getUsers().get(first).blockUser(db.getUsers().get(second));
                                }
                                break;
                            case 6:
                                String five = inputReader.readLine();
                                String six = inputReader.readLine();
                                try {
                                    int seven = Integer.parseInt(five);
                                    if (six != null) {
                                        result = db.addResearcher(seven, six);
                                    } else {
                                        result = false;
                                    }
                                } catch (NumberFormatException e) {
                                    result = false;
                                }
                                break;
                            case 7:
                                result = db.outputDatabase();
                                break;
                            default:
                                // nothign to do
                        }

                        if (result) {
                            mainOutWriter.write(command + " Success");
                        } else {
                            mainOutWriter.write(command + " Failure");
                        }
                        mainOutWriter.newLine();

                    } catch (NumberFormatException e) {
                        mainOutWriter.write("Command Failure");
                        mainOutWriter.newLine();
                    } catch (IOException e) {
                        mainOutWriter.write("IO Read Failure");
                        mainOutWriter.newLine();
                    } catch (BadException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                System.out.println("IO Read Failure");
            }
        } catch (IOException e) {
            System.out.println("IO Read Failure");
        }
    }

    }
}