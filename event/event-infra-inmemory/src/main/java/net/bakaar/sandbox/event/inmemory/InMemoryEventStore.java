package net.bakaar.sandbox.event.inmemory;

import net.bakaar.sandbox.event.domain.Event;
import net.bakaar.sandbox.event.domain.EventStore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStore implements EventStore {
    private List<InMemoryEventRaised> allEvents = new ArrayList<>();

    @Override
    public void store(Event event) {
        InMemoryEventRaised eventRaised = new InMemoryEventRaised(event).raisedAt(Instant.now());
        allEvents.add(eventRaised);
    }
}
