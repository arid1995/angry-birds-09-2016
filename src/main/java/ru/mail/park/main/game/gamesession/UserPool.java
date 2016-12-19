package ru.mail.park.main.game.gamesession;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class UserPool {
    private ConcurrentLinkedQueue<GameUser> users;

    public UserPool() {
        users = new ConcurrentLinkedQueue<>();
    }

    public void addUser(GameUser user) {
        users.add(user);
    }

    public GameUser getUser() {
        return users.poll();
    }

    public ConcurrentLinkedQueue<GameUser> getUsers() {
        return users;
    }
}
