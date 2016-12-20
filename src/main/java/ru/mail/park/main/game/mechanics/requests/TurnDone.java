package ru.mail.park.main.game.mechanics.requests;

/**
 * Created by farid on 20.12.16.
 */
public class TurnDone {
    private Long room;

    public TurnDone(Long room) {
        this.room = room;
    }

    public TurnDone() {
    }

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }
}
