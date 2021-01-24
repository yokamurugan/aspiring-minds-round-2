package securedchat.protocol;

public class Message {

    private MessageType _type;
    private String _from;
    private String _to;
    private String _data;

    public Message(String encodedMessage) {
        String[] parts = encodedMessage.split(":");
        _type = MessageType.getEnum(parts[0]);
        _from = parts[1];
        _to = parts[2];
        _data = parts[3];
    }

    public Message() {
        _to = "*";
    }

    public Message(MessageType type, String from) {
        this();
        _type = type;
        _from = from;
        _data = "*";
    }

    public Message(MessageType type, String from, String data) {
        this(type, from);
        _data = data;
    }

    public Message(MessageType type, String from, String data, String to) {
        this(type, from, data);
        _to = to;
    }

    public MessageType getType() {
        return _type;
    }

    public String getFrom() {
        return _from;
    }

    public String getTo() {
        return _to;
    }

    public String getData() {
        return _data;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s:%s", _type, _from, _to, _data);
    }
}   