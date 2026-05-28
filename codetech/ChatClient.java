import java.io.*;
import java.net.*;

// Client class
public class ChatClient {

    public static void main(String[] args) {
        try {
            // Connect to server (localhost + port)
            Socket socket = new Socket("localhost", 1234);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = serverIn.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readThread.start();

            // Send messages to server
            String userMsg;
            while ((userMsg = input.readLine()) != null) {
                serverOut.println(userMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}