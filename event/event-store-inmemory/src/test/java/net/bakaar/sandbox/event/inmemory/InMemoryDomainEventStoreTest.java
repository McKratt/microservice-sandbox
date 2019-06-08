package net.bakaar.sandbox.event.inmemory;

import net.bakaar.sandbox.event.domain.DomainEvent;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class InMemoryDomainEventStoreTest {

    private InMemoryEventStore store = new InMemoryEventStore();

    @Test
    public void emit_should_store_event_localy() {
        //Given
        DomainEvent event = mock(DomainEvent.class);
        Instant before = Instant.now();
        //When
        store.store(event);
        //Then
        Instant after = Instant.now();
        InMemoryEventRaised raisedEvent = ((List<InMemoryEventRaised>) ReflectionTestUtils.getField(store, "allEvents")).get(0);
        assertThat(raisedEvent.getEvent()).isEqualTo(event);
        assertThat(raisedEvent.raiseAt()).isBetween(before, after);
    }
}