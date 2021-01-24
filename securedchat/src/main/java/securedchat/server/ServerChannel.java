package securedchat.server;

import java.io.*;
import java.util.*;
import java.net.*;

import securedchat.protocol.*;

public class ServerChannel extends Thread {

    private static Map<String, Socket> clients = new HashMap<String, Socket>();
    private static Map<String, String> publicKeys = new HashMap<String, String>();

    private String _clientName;
    private Socket _clientSocket;
    private BufferedReader _request;

    public ServerChannel(Socket clientSocket) throws IOException {
        _clientSocket = clientSocket;
        _request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {     
        try {
            Message message = receive();
            onJoined(message);

            while (true) {
                message = receive();
                switch (message.getType()) {
                    case MESSAGE:           broadcast(message); break;
                    case SECRET_MESSAGE:    sendMessage(message); break;
                    case QUIT:              onQuit(message);  break;
                    default:
                        break;  // just ignore
                }
            }
        } 
        catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    private Message receive() throws IOException {
        String request = _request.readLine();
        System.out.println(request);

        return new Message(request);
    }

    private void onJoined(Message message) throws IOException {
        _clientName = message.getFrom();
        
        ServerChannel.clients.put(_clientName, _clientSocket);
        ServerChannel.publicKeys.put(_clientName, message.getData());

        sendClientList();
        broadcast(message);
    }

    private void onQuit(Message message) throws IOException {
        ServerChannel.clients.remove(_clientName);
        broadcast(message);
    }

    private void broadcast(Message message) throws IOException {
        for(String clientName : ServerChannel.clients.keySet()) {
            if (!clientName.equals(_clientName)) {
                sendMessage(message, clientName);
            }
        }
    }

    private void sendClientList() throws IOException {
        for(String clientName : ServerChannel.clients.keySet()) {
            if (!clientName.equals(_clientName)) {

                String publicKey = ServerChannel.publicKeys.get(clientName);
                Message message = new Message(MessageType.JOIN, clientName, publicKey);

                sendMessage(message, _clientName);
            }
         }
    }

    private void sendMessage(Message message) throws IOException {
        sendMessage(message, message.getTo());
    }

    private void sendMessage(Message message, String clientName) throws IOException {
        Socket clientSocket = ServerChannel.clients.get(clientName);
        OutputStream response = clientSocket.getOutputStream();
        String serialziedMessage = message.toString();
        byte[] messageBytes = (serialziedMessage + "\n").getBytes("UTF8");

        response.write(messageBytes);             
        response.flush();
    }
}