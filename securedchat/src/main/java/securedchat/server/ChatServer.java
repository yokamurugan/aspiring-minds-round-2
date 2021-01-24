package securedchat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static final int DEFAULT_PORT = 5000;

    public static void main(String args[]) throws IOException  {
        int port = getPort(args);               

        ServerSocket server = new ServerSocket(port); 
        log("Http Server listening on port: " + port);

        // The server loop
        while (true) {
            // Wait for a client to connect
            Socket client = server.accept();
            log("Client connected IP:" + client.getInetAddress() + " port:" + client.getPort());

            // Create a new thread to process the client
            new ServerChannel(client).start();
        }
    }

    // Take a port number from command line if one was specified
    private static int getPort(String args[]) {
        int port = DEFAULT_PORT;    

        if (args.length >= 1) {
            try {
                // If user specifed a port, use it
                port = Integer.parseInt(args[0]);
            } 
            catch (NumberFormatException e) { 
                log("Invalid port specified: " + args[0]);
                log("Falling back to default port.");
            }  
        }

        return port;
    }

    private static void log(String message) {
        System.out.println(message);
    }
}