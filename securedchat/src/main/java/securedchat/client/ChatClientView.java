package securedchat.client;

import java.util.*;
import java.io.*;

public class ChatClientView {
    private final String CMD_QUIT = "!q";
    private final String CMD_START_SECURED = "!s";
    private final String CMD_END_SECURED = "!x";
    
    private List<ChatMessage> _chatMessages;
    private String _toUser;
    private String _clientName;

    private ChatClient _app;
    
    public ChatClientView(String clientName, ChatClient app) {
        _clientName = clientName;
        _app = app;
        _toUser = "";
        _chatMessages = new ArrayList<ChatMessage>();
    }

    public void inputLoop() throws Exception {
        Console.clear();
        displayPrompt();

        while (true) {
            String message = Console.readLine();
            
                 if (message.startsWith(CMD_QUIT))              break;
            else if (message.startsWith(CMD_START_SECURED))     startSecuredChat(message);
            else if (message.startsWith(CMD_END_SECURED))       endSecuredChat();
            else                                                sendMessage(message);
        }
    }

    public void notifyJoined(String userName) {
        addInfoMessage(userName + " has joined.");
    }

    public void notifyLeft(String userName) {
        addInfoMessage(userName + " has left.");
    }

    public void notifyChatMessage(String message, String from, String to) {
        addNewMessage(new ChatMessage(message, from, to));
    }
    
    private void startSecuredChat(String message) {
        String[] parts = message.split(" ");
        String toUser = parts[1];

        if (_app.isUserAvailable(toUser)) {
            _toUser = toUser;
            addInfoMessage("Private chat with " + _toUser + " started.");
        } else
            addInfoMessage(toUser + " is offline.");
    }

    private void endSecuredChat() {
        _toUser = "";
        addInfoMessage("Private chat ended.");
    }

    private void sendMessage(String message) throws Exception {
        _app.sendMessage(message, _toUser);
        addNewMessage(new ChatMessage(message, _clientName, _toUser));
    }

    private void addInfoMessage(String message) {
        addNewMessage(new ChatMessage(message, true));
    }

    private void addNewMessage(ChatMessage chatMessage) {
        synchronized(this) {
            _chatMessages.add(chatMessage);
            refresh();
        }
    }

    private void refresh() {
        int row = Console.MAX_ROW - _chatMessages.size();          

        Console.clear();
        for(ChatMessage msg : _chatMessages) {
            displayChatMessage(msg, row);
            row++;
        }
        
        displayPrompt();
    }

    private void displayChatMessage(ChatMessage msg, int row) {
        String name = msg.getFrom();
        String color = msg.getFrom().equals(_clientName) ? Console.ANSI_GREEN :
                       msg.getIsInfo() ? Console.ANSI_YELLOW : Console.ANSI_CYAN;
        
        Console.setLocation(row, 1);
        Console.print(color);
        Console.print(String.format("[%s] ", msg.getTitle()));
        Console.print(Console.ANSI_RESET);
        Console.print(msg.getMessage());
    }        

    private void displayPrompt() {
        Console.setLocation(Console.MAX_ROW, 1);
        Console.print(_clientName + (_toUser.length() > 0 ? "->" + _toUser : "") + " $ ");
    }    
}