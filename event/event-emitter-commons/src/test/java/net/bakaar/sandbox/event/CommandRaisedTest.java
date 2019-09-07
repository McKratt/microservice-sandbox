package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CommandRaisedTest {

    @Test
    void constructor_should_set_raisedAt() {
        // Given
        // When
        CommandRaised commandRaised = new CommandRaised(mock(DomainCommand.class));
        // Then
        assertThat(commandRaised).extracting(CommandRaised::getRaisedAt, CommandRaised::getId).isNotNull();
    }
}