package net.bakaar.sandbox.event;

public interface MessageRaisedEntityMapper {
    MessageRaisedEntity mapToEntity(CommandRaised command);
}
