package net.bakaar.sandbox.cas.infra.event.db;

import net.bakaar.sandbox.event.common.DomainEvent;
import net.bakaar.sandbox.event.common.DomainEventEmitter;

public class DBDomainEventEmitter implements DomainEventEmitter {

    private final EventRaisedRepository repository;
    private EventRaisedFactory factory;

    public DBDomainEventEmitter(EventRaisedRepository repository, EventRaisedFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public void emit(DomainEvent event) {

        repository.save(factory.fromEvent(event));
    }
}
