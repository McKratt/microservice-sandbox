package net.bakaar.sandbox.event.domain;

public interface DomainEventEmitter {

    void emit(DomainEvent event);
}
