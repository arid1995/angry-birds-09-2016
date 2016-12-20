package ru.mail.park.main.game.mechanics.requests;

/**
 * Created by farid on 20.12.16.
 */
public class FieldState {
    private Long room;

    private String objects;

    public FieldState() {
    }

    public FieldState(Long room, String objects) {
        this.room = room;
        this.objects = objects;
    }

    public Long getRoom() {
        return room;
    }

    public String getObjects() {
        return objects;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }
}
