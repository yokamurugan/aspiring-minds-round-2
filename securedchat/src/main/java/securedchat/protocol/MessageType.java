package securedchat.protocol;

import java.util.*;

public enum MessageType {
    JOIN("j"),
    QUIT("q"),
    MESSAGE("m"),
    SECRET_MESSAGE("s"),
    UNKNOWN("?");

    private String _name;

    private MessageType(final String name) {
        _name = name;
    }

    public static MessageType getEnum(String messageName) {
        for (MessageType item : Arrays.asList(MessageType.values())) {
            if (item.toString().equals(messageName))
                return item;
        }

        return MessageType.UNKNOWN;
    }

    @Override
    public String toString() {
        return _name;
    }
}   