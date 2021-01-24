package securedchat.client;

import java.io.*;
import java.util.*;
import java.net.*;

import securedchat.protocol.*;

public class ClientChannel extends Observable implements Runnable {

    private BufferedWriter _request;
    private BufferedReader _response;

    public ClientChannel(String host, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        _request = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
        _response = new BufferedReader(new InputStreamReader(socket.getInputStream()));        
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String response = _response.readLine();
                setChanged();
                notifyObservers(new Message(response));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void send(Message request) throws IOException {
        _request.write(request.toString() + "\n");
        _request.flush(); 
    }
}