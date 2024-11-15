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
        BufferedWriter bw = null;

        try {
            socket = new Socket("localhost", 1234);

            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());

            br = new BufferedReader(in);
            bw = new BufferedWriter(out);

            Scanner scan = new Scanner(System.in);

            while (true) {
                String existing = scan.nextLine();
                bw.write(existing);
                bw.newLine();
                bw.flush();

                System.out.println("Server" + br.readLine());
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
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
