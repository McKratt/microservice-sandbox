package net.bakaar.sandbox.event.domain.springdata;

import net.bakaar.sandbox.event.domain.DomainEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DBDomainEventEmitterTest {

    @Mock
    private EventRaisedRepository repository;
    @Mock
    private DBEventRaisedFactory factory;
    @InjectMocks
    private DBEventStore emitter;

    @Test
    public void emit_should_save_event_in_db() {
        // Given
        DomainEvent event = mock(DomainEvent.class);
        DBEventRaised raised = mock(DBEventRaised.class);
        given(factory.fromEvent(event)).willReturn(raised);
        // When
        emitter.store(event);
        // Then
        ArgumentCaptor<DBEventRaised> captor = ArgumentCaptor.forClass(DBEventRaised.class);
        verify(repository).save(captor.capture());
        DBEventRaised DBEventRaised = captor.getValue();
        assertThat(DBEventRaised).isEqualTo(raised);
    }
}