package net.bakaar.sandbox.event.store;

import net.bakaar.sandbox.event.domain.DomainEvent;

public interface EventStore {

    void store(DomainEvent event);
}
