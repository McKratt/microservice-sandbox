package net.bakaar.sandbox.event;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
class EventRaised<T extends DomainEvent> {
    private final T event;
    private final LocalDateTime raisedAt;
    private final UUID id;

    public EventRaised(T event) {
        this.event = event;
        this.raisedAt = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }
}
