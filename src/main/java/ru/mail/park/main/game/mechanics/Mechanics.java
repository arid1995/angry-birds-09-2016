package ru.mail.park.main.game.mechanics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.mail.park.main.game.Utils;
import ru.mail.park.main.game.gamesession.GameRooms;
import ru.mail.park.main.game.gamesession.GameUser;
import ru.mail.park.main.game.gamesession.Room;
import ru.mail.park.main.game.gamesession.UserPool;
import ru.mail.park.main.game.websocket.MessageContainer;

import java.io.IOException;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class Mechanics {
    @Autowired
    private UserPool pool;
    @Autowired
    private MessageContainer container;
    @Autowired
    private Utils utils;
    @Autowired
    GameRooms rooms;
    ObjectMapper mapper = new ObjectMapper();


    public void handle() {
        createSessions();
    }

    private void createSessions() {
        GameUser user1;
        GameUser user2 = null;

        while ((user1 = pool.getUser()) != null) {
            user2 = pool.getUser();

            if (user2 == null) {
                pool.addUser(user1);
                break;
            }

            final Room room = new Room(user1, user2);
            rooms.addRoom(room);

            final ObjectNode responseForFirst = mapper.createObjectNode();
            final ObjectNode responseForSecond = mapper.createObjectNode();

            responseForFirst.put("name", user2.getUsername())
                    .put("turn", true)
                    .put("room", room.getId());

            responseForSecond.put("name", user1.getUsername())
                    .put("turn", false)
                    .put("room", room.getId());

            try {
                user1.getSession().sendMessage(new TextMessage(utils.buildResponse("opponentFound", responseForFirst)));
                user2.getSession().sendMessage(new TextMessage(utils.buildResponse("opponentFound", responseForSecond)));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
