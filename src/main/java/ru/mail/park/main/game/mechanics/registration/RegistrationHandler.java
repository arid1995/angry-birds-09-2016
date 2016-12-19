package ru.mail.park.main.game.mechanics.registration;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.park.main.game.gamesession.UserPool;
import ru.mail.park.main.game.websocket.MessageHandler;

/**
 * Created by farid on 19.12.16.
 */
public class RegistrationHandler {
    @Autowired
    UserPool pool;

    public void handleMessage() {}
}
