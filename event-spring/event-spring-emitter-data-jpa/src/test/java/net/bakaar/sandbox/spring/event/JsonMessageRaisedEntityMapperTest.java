package net.bakaar.sandbox.spring.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.domain.DomainCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JsonMessageRaisedEntityMapperTest {

    @Test
    void mapToEntity_should_map_all_fields() throws JsonProcessingException {
        // Given
        DomainCommand command = mock(DomainCommand.class);
        CommandRaised commandRaised = new CommandRaised(command);
        ObjectMapper jsonMapper = mock(ObjectMapper.class);
        String serializedCommand = "Serialized Command";
        given(jsonMapper.writeValueAsString(command)).willReturn(serializedCommand);
        JsonMessageRaisedEntityMapper mapper = new JsonMessageRaisedEntityMapper(jsonMapper);
        // When
        MessageRaisedEntity entity = mapper.mapToEntity(commandRaised);
        // Then
        assertThat(entity).isNotNull()
                .extracting(MessageRaisedEntity::getId, MessageRaisedEntity::getRaisedAt, MessageRaisedEntity::getPayload)
                .contains(commandRaised.getId(), commandRaised.getRaisedAt(), serializedCommand);
    }

    private class TestCommand implements DomainCommand {
    }
}