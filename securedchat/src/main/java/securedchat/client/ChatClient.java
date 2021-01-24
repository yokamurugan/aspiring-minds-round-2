package securedchat.client;

import java.util.*;
import java.io.*;
import java.security.KeyPair;
import org.apache.commons.codec.binary.Base64;

import securedchat.encryption.*;
import securedchat.protocol.*;

public class ChatClient implements Observer {

    private String _clientName;
    private ChatClientView _view;
    private ClientChannel _channel;

    private KeyPair _keyPair;
    private Map<String, String> _publicKeys;

    public static void main(String[] args) throws Exception {
        String host = args[0];             
        int port = new Integer(args[1]);   
        String clientName = args[2]; 

        ChatClient app = new ChatClient(host, port, clientName);
        app.run();
    }

    public ChatClient(String host, int port, String clientName) throws Exception {
        _clientName = clientName;
        _channel = new ClientChannel(host, port);
        _view = new ChatClientView(_clientName, this);
        _publicKeys = new HashMap<String, String>();
    }

    public void run() throws Exception {
        _keyPair = RSACipher.generateKeyPair();

        _channel.addObserver(this);
        new Thread(_channel).start();

        _channel.send(new Message(MessageType.JOIN, _clientName, this.getPublicKey()));
        _view.inputLoop();
        _channel.send(new Message(MessageType.QUIT, _clientName));
        System.exit(0);
    }

    public boolean isUserAvailable(String toUser) {
        return _publicKeys.containsKey(toUser);
    }

    public void sendMessage(String message, String toUser) throws Exception {
        MessageType type = MessageType.MESSAGE;
        String data = message;

        if (toUser.length() > 0) {
            type = MessageType.SECRET_MESSAGE;
            byte[] publicKey = new Base64().decode(_publicKeys.get(toUser));
            data = End2EndEncryption.encrypt(message, publicKey);
        }

        _channel.send(new Message(type, _clientName, data, toUser));
    }

    public void update(Observable obj, Object arg) {
        Message message = (Message)arg;

        try {
            // What to do?
            switch (message.getType()) {
                case JOIN:              onJoined(message); break;
                case QUIT:              onQuit(message); break;
                case MESSAGE:           onMessage(message); break;
                case SECRET_MESSAGE:    onSecuredMessage(message); break;
                default:
                    return; // Ignore
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onJoined(Message message) {
        String newUserName = message.getFrom(); 
        String publicKey = message.getData();

        _publicKeys.put(newUserName, publicKey);
        _view.notifyJoined(newUserName);
    }

    private void onQuit(Message message) {
        String userName = message.getFrom(); 

        _publicKeys.remove(userName);
        _view.notifyLeft(userName);
    }

    private void onMessage(Message message) {        
        _view.notifyChatMessage(message.getData(), message.getFrom(), "");
    }

    private void onSecuredMessage(Message message) {
        String encryptedMessage = message.getData();
        try {
            String plainMessage = End2EndEncryption.decrypt(encryptedMessage, _keyPair.getPrivate().getEncoded());
            _view.notifyChatMessage(plainMessage, message.getFrom(), _clientName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }           
    }

    private String getPublicKey() {
        return new Base64().encodeAsString(_keyPair.getPublic().getEncoded());
    }    
}