package ru.mail.park.main.game.gamesession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.mail.park.main.game.Utils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class GameRooms {
    AtomicLong id = new AtomicLong();
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    UserPool pool;
    @Autowired
    Utils utils;
    private final HashMap<Long, Room> rooms;

    public GameRooms() {
        rooms = new HashMap<>();
    }

    public void addRoom(@NotNull Room room) {
        room.setId(id.getAndIncrement());
        rooms.put(room.getId(), room);
    }

    public HashMap<Long, Room> getRooms() {
        return rooms;
    }

    public void createRooms() {
        GameUser user1;
        GameUser user2 = null;

        while ((user1 = pool.getUser()) != null) {
            user2 = pool.getUser();

            if (user2 == null) {
                pool.addUser(user1);
                break;
            }

            final Room room = new Room(user1, user2);
            addRoom(room);

            user1.setRoom(room);
            user2.setRoom(room);
            user1.setTurn(true);
            user2.setTurn(false);

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

    public void deleteRooms() {
        // TODO
    }
}
