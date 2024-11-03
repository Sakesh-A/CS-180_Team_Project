import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new FileReader("input.txt"))) {
            String[] files = inputReader.readLine().split(" ");
            if (files.length < 4) {
                System.out.println("Insufficient file names in input.txt");
                return;
            }

            String scpIn = files[0];               // SCP input file
            String researcherIn = files[1];       // Researcher input file
            String databaseOutput = files[2];     // Database output file
            String mainOut = files[3];            // Main output file

            FoundationDatabase db = new FoundationDatabase(scpIn, researcherIn, databaseOutput);

            try (BufferedWriter mainOutWriter = new BufferedWriter(new FileWriter(mainOut))) {
                mainOutWriter.write("Foundation Database Started");
                mainOutWriter.newLine();

                String commandLine;

                while ((commandLine = inputReader.readLine()) != null) {
                    boolean result = false;

                    try {
                        int command = Integer.parseInt(commandLine);
                        switch (command) {
                            case 1:
                                result = db.readSCP();
                                break;
                            case 2:
                                result = db.readResearcher();
                                break;
                            case 3:
                                result = db.autoAssignResearcher();
                                break;
                            case 4:
                                String one = inputReader.readLine();
                                String two = inputReader.readLine();
                                if (one != null && two != null) {
                                    result = db.modifySCP(one, two);
                                } else {
                                    result = false;
                                }
                                break;
                            case 5:
                                String three = inputReader.readLine();
                                String four = inputReader.readLine();
                                if (three != null && four != null) {
                                    result = db.modifyResearcher(three, four);
                                } else {
                                    result = false;
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