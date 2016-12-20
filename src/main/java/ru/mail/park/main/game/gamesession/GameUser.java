package ru.mail.park.main.game.gamesession;

import org.hibernate.Session;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.NotNull;

/**
 * Created by farid on 18.12.16.
 */
public class GameUser {
    @NotNull
    private Integer id;

    @NotNull
    private String username;
    private GameUser opponent;
    private WebSocketSession session;
    private Room room;
    private boolean turn;

    public GameUser() {}

    public GameUser(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public GameUser getOpponent() {
        return opponent;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Room getRoom() {
        return room;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setOpponent(GameUser opponent) {
        this.opponent = opponent;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
