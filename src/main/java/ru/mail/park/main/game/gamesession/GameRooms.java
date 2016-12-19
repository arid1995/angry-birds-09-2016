package ru.mail.park.main.game.gamesession;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class GameRooms {
    AtomicLong id = new AtomicLong();
    private final ArrayList<Room> rooms;

    public GameRooms() {
        rooms = new ArrayList<>();
    }

    public void addRoom(@NotNull Room room) {
        room.setId(id.getAndIncrement());
        rooms.add(room);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
