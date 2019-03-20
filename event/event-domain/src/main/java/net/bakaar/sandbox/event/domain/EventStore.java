package net.bakaar.sandbox.event.domain;

public interface EventStore {

    void store(DomainEvent event);
}
