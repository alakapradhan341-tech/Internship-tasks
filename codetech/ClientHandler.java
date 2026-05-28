import java.io.*;
import java.net.*;
import java.util.*;

// Handles each client separately using thread
public class ClientHandler implements Runnable {

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    Vector<ClientHandler> clientList;
    String name;

    // Constructor
    public ClientHandler(Socket socket, Vector<ClientHandler> clientList) {
        this.socket = socket;
        this.clientList = clientList;

        try {
            // Input stream from client
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Output stream to client
            out = new PrintWriter(socket.getOutputStream(), true);

            // Ask client name
            out.println("Enter your name:");
            name = in.readLine();

            broadcast("🔵 " + name + " joined the chat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Run method for thread
    public void run() {
        String message;

        try {
            // Keep reading messages from client
            while ((message = in.readLine()) != null) {

                // Exit condition
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Send message to all clients
                broadcast(name + ": " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove client on exit
            try {
                clientList.remove(this);
                socket.close();
                broadcast("🔴 " + name + " left the chat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send message to all clients
    void broadcast(String msg) {
        for (ClientHandler client : clientList) {
            client.out.println(msg);
        }
    }
}