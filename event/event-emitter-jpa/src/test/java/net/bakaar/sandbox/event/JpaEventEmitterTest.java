package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JpaEventEmitterTest {

    @Test
    void emit_command_should_map_command_to_entity() {
        // Given
        DomainCommand command = mock(DomainCommand.class);

        MessageRaisedEntityMapper messageRaisedEntityMapper = mock(MessageRaisedEntityMapper.class);
        MessageRaisedEntity messageRaisedEntity = mock(MessageRaisedEntity.class);
        given(messageRaisedEntityMapper.mapToEntity(any(CommandRaised.class))).willReturn(messageRaisedEntity);

        MessageProducer messageProducer = mock(MessageProducer.class);

        MessageRaisedRepository repository = mock(MessageRaisedRepository.class);

        JpaEventEmitter emitter = new JpaEventEmitter(messageProducer, messageRaisedEntityMapper, repository);
        // When
        emitter.emit(command);
        // Then
        ArgumentCaptor<CommandRaised> commandRaisedCaptor = ArgumentCaptor.forClass(CommandRaised.class);
        verify(messageRaisedEntityMapper).mapToEntity(commandRaisedCaptor.capture());
        verify(repository).put(messageRaisedEntity);
        verify(messageProducer).produce(commandRaisedCaptor.capture());
        assertThat(commandRaisedCaptor.getAllValues()).extracting(CommandRaised::getCommand).containsOnly(command);
    }
}