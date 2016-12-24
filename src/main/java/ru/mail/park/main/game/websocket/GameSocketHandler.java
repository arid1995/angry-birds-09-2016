package ru.mail.park.main.game.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.mail.park.main.game.Utils;
import ru.mail.park.main.game.gamesession.GameRooms;
import ru.mail.park.main.game.gamesession.GameUser;
import ru.mail.park.main.game.gamesession.UserPool;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by farid on 13.12.16.
 */
public class GameSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    Utils utils;

    @Autowired
    UserPool pool;

    @Autowired
    GameRooms rooms;

    HashMap<String, GameUser> connectedUsers = new HashMap<>();

    @Autowired
    MessageContainer messageContainer;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws AuthenticationException {
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws AuthenticationException {
        try {
            Message msg = mapper.readValue(message.getPayload(), Message.class);

            if (msg.getType().equals("ready")) {
                final GameUser user = mapper.readValue(msg.getData(), GameUser.class);
                user.setSession(session);
                session.sendMessage(new TextMessage(utils.buildResponse("confirmRequest", mapper.createObjectNode())));
                pool.addUser(user);
                connectedUsers.put(session.getId(), user);
            }

            messageContainer.addMessage(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        final GameUser disconnectedUser = connectedUsers.get(webSocketSession.getId());

        connectedUsers.remove(webSocketSession.getId());
        pool.removeUser(disconnectedUser);

        final GameUser opponent = disconnectedUser.getOpponent();

        if(disconnectedUser.getRoom() != null) {
            rooms.deleteRoom(disconnectedUser.getRoom().getId());
        }

        if (opponent != null) {
            opponent.getSession().sendMessage(new TextMessage(utils.buildResponse("trouble", "disconnected")));
            pool.addUser(disconnectedUser.getOpponent());
            disconnectedUser.setOpponent(null);
            opponent.setOpponent(null);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
