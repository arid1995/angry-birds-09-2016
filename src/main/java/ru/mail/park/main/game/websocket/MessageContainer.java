package ru.mail.park.main.game.websocket;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by farid on 19.12.16.
 */
@Service
public class MessageContainer<T> {
    private ConcurrentLinkedQueue<Message> messages;

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getMessage() {
        return messages.poll();
    }
}
