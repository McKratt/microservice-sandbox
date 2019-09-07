package net.bakaar.sandbox.spring.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.MessageRaisedEntityMapper;

@Slf4j
class JsonMessageRaisedEntityMapper implements MessageRaisedEntityMapper {

    private final ObjectMapper mapper;

    JsonMessageRaisedEntityMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MessageRaisedEntity mapToEntity(CommandRaised command) {
        MessageRaisedEntity entity = new MessageRaisedEntity();
        entity.setRaisedAt(command.getRaisedAt());
        entity.setId(command.getId());
        try {
            entity.setPayload(mapper.writeValueAsString(command.getCommand()));
        } catch (JsonProcessingException e) {
            LOG.error("Error during command mapping to json");
            throw new RuntimeException(e);
            //TODO refactor this error with a customized one
        }
        return entity;
    }
}
