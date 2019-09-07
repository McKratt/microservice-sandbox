package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainEvent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EventRaisedTest {

    @Test
    void constructor_should_set_raisedAt() {
        // Given
        // When
        EventRaised eventRaised = new EventRaised(mock(DomainEvent.class));
        // Then
        assertThat(eventRaised).extracting(EventRaised::getRaisedAt).isNotNull();
    }
}