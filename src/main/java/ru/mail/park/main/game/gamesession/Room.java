package ru.mail.park.main.game.gamesession;

import javax.validation.constraints.NotNull;

/**
 * Created by farid on 19.12.16.
 */
public class Room {
    @NotNull
    private Long id;
    @NotNull
    private GameUser user1;
    @NotNull
    private GameUser user2;

    public Room(@NotNull GameUser user1, @NotNull GameUser user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Long getId() {
        return id;
    }

    public GameUser getUser1() {
        return user1;
    }

    public GameUser getUser2() {
        return user2;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser1(@NotNull GameUser user1) {
        this.user1 = user1;
    }

    public void setUser2(@NotNull GameUser user2) {
        this.user2 = user2;
    }
}
