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
import ru.mail.park.main.game.mechanics.requests.FieldState;
import ru.mail.park.main.game.mechanics.requests.TurnDone;
import ru.mail.park.main.game.websocket.Message;
import ru.mail.park.main.game.websocket.MessageContainer;

import java.io.IOException;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class Mechanics {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserPool pool;
    @Autowired
    private MessageContainer container;
    @Autowired
    private Utils utils;
    @Autowired
    private GameRooms rooms;

    @SuppressWarnings("OverlyBroadCatchBlock")
    public void handle() {
        rooms.createRooms();
        Message message;
        Long deffectiveRoom = null;
        while ((message = container.getMessage()) != null) {
            try {
                if (message.getType().equals("fieldState")) {
                    FieldState state = mapper.readValue(message.getData(), FieldState.class);
                    final Room room = rooms.getRooms().get(state.getRoom());

                    if (room == null) continue;

                    deffectiveRoom = room.getId();

                    if(room.getUser1().getTurn()) {
                        room.getUser2().getSession().sendMessage(new TextMessage(utils.buildResponse("fieldState",
                                state.getObjects())));
                        return;
                    }

                    room.getUser1().getSession().sendMessage(new TextMessage(utils.buildResponse("fieldState",
                            state.getObjects())));
                }

                if (message.getType().equals("done")) {
                    final TurnDone done = mapper.readValue(message.getData(), TurnDone.class);
                    final Room room = rooms.getRooms().get(done.getRoom());
                    if(room.getUser1().getTurn()) {
                        room.getUser1().setTurn(false);
                        room.getUser2().setTurn(true);

                        room.getUser1().getSession().sendMessage(new TextMessage(utils.buildResponse("event",
                                mapper.createObjectNode().put("turn", false))));

                        room.getUser2().getSession().sendMessage(new TextMessage(utils.buildResponse("event",
                                mapper.createObjectNode().put("turn", true))));
                        return;
                    }

                    room.getUser1().setTurn(true);
                    room.getUser2().setTurn(false);

                    room.getUser1().getSession().sendMessage(new TextMessage(utils.buildResponse("event",
                            mapper.createObjectNode().put("turn", true))));

                    room.getUser2().getSession().sendMessage(new TextMessage(utils.buildResponse("event",
                            mapper.createObjectNode().put("turn", false))));

                }
            } catch (IOException ex) {
                rooms.deleteRoom(deffectiveRoom);
                ex.printStackTrace();
            }
        }
    }

    private void createRooms() {

    }
}
