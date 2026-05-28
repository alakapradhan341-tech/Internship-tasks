import java.io.*;
import java.net.*;
import java.util.*;

// Main server class
public class ChatServer {

    // List to store all connected clients
    static Vector<ClientHandler> clientList = new Vector<>();

    public static void main(String[] args) {
        try {
            // Create server socket on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started... Waiting for clients");

            // Infinite loop to accept clients
            while (true) {
                Socket socket = serverSocket.accept(); // Accept client
                System.out.println("New client connected");

                // Create handler for each client
                ClientHandler handler = new ClientHandler(socket, clientList);

                // Add to client list
                clientList.add(handler);

                // Start thread
                Thread t = new Thread(handler);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}