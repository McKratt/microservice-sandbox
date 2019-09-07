package net.bakaar.sandbox.event;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MessageRaised {
    protected final LocalDateTime raisedAt;
    protected final UUID id;

    public MessageRaised() {
        this.raisedAt = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }
}
