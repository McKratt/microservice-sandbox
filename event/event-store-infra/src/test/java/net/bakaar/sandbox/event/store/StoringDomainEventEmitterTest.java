package net.bakaar.sandbox.event.store;

import net.bakaar.sandbox.event.domain.DomainEvent;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StoringDomainEventEmitterTest {

    @Test
    public void emit_should_store_event() {
        //Given
        EventStore store = mock(EventStore.class);
        StoringDomainEventEmitter emitter = new StoringDomainEventEmitter(store);
        DomainEvent event = mock(DomainEvent.class);
        //When
        emitter.emit(event);
        //Then
        verify(store).store(event);
    }
}