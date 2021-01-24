package securedchat.client;

public class ChatMessage {
    private String _message;
    private String _from = "";
    private String _to = "";
    private boolean _isInfo = false;

    public ChatMessage(String message) {
        _message = message;
    }

    public ChatMessage(String message, boolean isInfo) {
        this(message);
        _isInfo = isInfo;
    }

    public ChatMessage(String message, String from, String to) {
        this(message);
        _from = from;
        _to = to;
    }

    public String getFrom() {
        return _from;
    }

    public String getTitle() {
        return _isInfo ? "INFO" : 
               _from + (_to.length() > 0 ? "->" + _to : "");
    }

    public String getMessage() {
        return _message;
    }

    public boolean getIsInfo() {
        return _isInfo;
    }
}