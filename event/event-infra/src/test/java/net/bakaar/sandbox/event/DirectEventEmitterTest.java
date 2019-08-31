package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.event.domain.DomainEventEmitter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DirectEventEmitterTest {


    @Test
    void emit_should_call_message_producer() {
        // Given
        DomainEvent event = mock(DomainEvent.class);
        MessageProducer producer = mock(MessageProducer.class);
        DomainEventEmitter emitter = new DirectEventEmitter(producer);
        // When
        emitter.emit(event);
        // Then
        ArgumentCaptor<EventRaised> eventRaisedCaptor = ArgumentCaptor.forClass(EventRaised.class);
        verify(producer).produce(eventRaisedCaptor.capture());
        assertThat(eventRaisedCaptor.getValue().getEvent()).isSameAs(event);
    }
}