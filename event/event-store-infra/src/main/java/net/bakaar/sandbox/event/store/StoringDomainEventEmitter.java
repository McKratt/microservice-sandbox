package net.bakaar.sandbox.event.store;

import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.event.domain.DomainEventEmitter;

public class StoringDomainEventEmitter implements DomainEventEmitter {

    private final EventStore store;

    public StoringDomainEventEmitter(EventStore store) {
        this.store = store;
    }

    @Override
    public void emit(DomainEvent event) {
        store.store(event);
    }
}
