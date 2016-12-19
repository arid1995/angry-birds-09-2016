package ru.mail.park.main.game.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mail.park.main.game.gamesession.GameUser;

import java.io.IOException;

/**
 * Created by farid on 18.12.16.
 */
public abstract class MessageHandler<T> {
    private final Class<T> clazz;

    public MessageHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handleMessage(Message message, GameUser user) throws Exception {
        try {
            final Object data = new ObjectMapper().readValue(message.getData(), clazz);

            //noinspection ConstantConditions
            handle(clazz.cast(data), user);
        } catch (IOException | ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    public abstract void handle(T handler, GameUser user);
}
