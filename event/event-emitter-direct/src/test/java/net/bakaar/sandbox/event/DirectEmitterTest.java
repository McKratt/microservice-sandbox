package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainCommand;
import net.bakaar.sandbox.event.domain.DomainEmitter;
import net.bakaar.sandbox.event.domain.DomainEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DirectEmitterTest {


    @Test
    void emit_domain_event_should_call_message_producer() {
        // Given
        DomainEvent event = mock(DomainEvent.class);
        MessageProducer producer = mock(MessageProducer.class);
        DomainEmitter emitter = new DirectEmitter(producer);
        // When
        emitter.emit(event);
        // Then
        ArgumentCaptor<EventRaised> eventRaisedCaptor = ArgumentCaptor.forClass(EventRaised.class);
        verify(producer).produce(eventRaisedCaptor.capture());
        assertThat(eventRaisedCaptor.getValue().getEvent()).isSameAs(event);
    }

    @Test
    void emit_domain_command_should_call_message_producer() {
        // Given
        DomainCommand command = mock(DomainCommand.class);
        MessageProducer producer = mock(MessageProducer.class);
        DomainEmitter emitter = new DirectEmitter(producer);
        // When
        emitter.emit(command);
        // Then
        ArgumentCaptor<CommandRaised> commandRaisedCaptor = ArgumentCaptor.forClass(CommandRaised.class);
        verify(producer).produce(commandRaisedCaptor.capture());
        assertThat(commandRaisedCaptor.getValue().getCommand()).isSameAs(command);
    }
}