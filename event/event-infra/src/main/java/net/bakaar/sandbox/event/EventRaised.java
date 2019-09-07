package net.bakaar.sandbox.event;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.DomainEvent;

@Getter
class EventRaised extends MessageRaised {
    private final DomainEvent event;

    public EventRaised(DomainEvent event) {
        super();
        this.event = event;
    }
}
