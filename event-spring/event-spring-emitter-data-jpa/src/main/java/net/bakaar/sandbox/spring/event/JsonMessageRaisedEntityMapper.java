package net.bakaar.sandbox.spring.event;

import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.MessageRaisedEntityMapper;

public class JsonMessageRaisedEntityMapper implements MessageRaisedEntityMapper {
    @Override
    public MessageRaisedEntity mapToEntity(CommandRaised command) {
        return null;
    }
}
