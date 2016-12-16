package ru.mail.park.main.game.mechanics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.mail.park.main.game.gamesession.LobbySessionService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by farid on 15.12.16.
 */
@Service
public class MechanicsExecutor implements Runnable {
    @Autowired
    private LobbySessionService lobby;

    private final long TICK = 20;

    private int position = 0;
    private int end = 1000;
    private int begin = 0;
    private int direction = 1;

    private Clock clock = Clock.systemDefaultZone();

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            final long before = clock.millis();
            try {

                final long after = clock.millis();

                Thread.sleep(TICK - (after - before));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
