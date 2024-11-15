import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader in = null;
        OutputStreamWriter out = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            Scanner sc = new Scanner(System.in);
            socket = new Socket("localhost", 4242);

            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());

            br = new BufferedReader(in);
            pw = new PrintWriter(out);

            System.out.println(br.readLine());
            String hasAccount = sc.nextLine();
            pw.println(hasAccount);
            pw.flush();

            if (hasAccount.equals("yes")) {
                while (true) {
                    System.out.print(br.readLine());
                    pw.println(sc.nextLine());
                    pw.flush();

                    System.out.println(br.readLine());
                    pw.println(sc.nextLine());
                    pw.flush();

                    String status = br.readLine();
                    System.out.println(status);
                    if (status.equals("Success")) {
                        break;
                    }
                }

            } else {
                while (true) {
                    System.out.print(br.readLine());
                    pw.println(sc.nextLine());
                    pw.flush();

                    System.out.println(br.readLine());
                    pw.println(sc.nextLine());
                    pw.flush();

                    System.out.print(br.readLine());
                    pw.println(sc.nextLine());
                    pw.flush();

                    String response = br.readLine();
                    System.out.println(response);
                    if (response.equals("Success")) {
                        break;
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
                if (pw != null) {
                    pw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
