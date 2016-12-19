package ru.mail.park.main.game.websocket;

/**
 * Created by farid on 19.12.16.
 */
public class Message {
    private String type;

    private String data;


    public Message(Class clazz, String data) {
        this(clazz.getName(), data);
    }

    public Message() {
    }

    public Message(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
