package net.bakaar.sandbox.event.inmemory;

import net.bakaar.sandbox.event.domain.DomainEvent;

import java.time.Instant;

public class InMemoryEventRaised {
    private final DomainEvent event;
    private Instant raised;

    InMemoryEventRaised(DomainEvent event) {
        this.event = event;
    }

    InMemoryEventRaised raisedAt(Instant raised) {
        this.raised = raised;
        return this;
    }

    public DomainEvent getEvent() {
        return event;
    }

    public Instant raiseAt() {
        return raised;
    }
}
