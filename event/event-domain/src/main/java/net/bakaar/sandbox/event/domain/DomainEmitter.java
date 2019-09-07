package net.bakaar.sandbox.event.domain;

public interface DomainEmitter {

    void emit(DomainEvent event);

    void emit(DomainCommand command);
}
