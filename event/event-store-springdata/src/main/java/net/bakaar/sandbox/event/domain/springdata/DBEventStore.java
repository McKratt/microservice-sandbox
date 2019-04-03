package net.bakaar.sandbox.event.domain.springdata;

import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.event.store.EventStore;

public class DBEventStore implements EventStore {

    private final EventRaisedRepository repository;
    private DBEventRaisedFactory factory;

    public DBEventStore(EventRaisedRepository repository, DBEventRaisedFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public void store(DomainEvent event) {

        repository.save(factory.fromEvent(event));
    }
}
