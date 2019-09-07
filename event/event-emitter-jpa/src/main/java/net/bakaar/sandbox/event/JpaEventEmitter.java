package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainCommand;
import net.bakaar.sandbox.event.domain.DomainEmitter;
import net.bakaar.sandbox.event.domain.DomainEvent;

/**
 * This emitter keep the emitted object in a JPA database.
 */
public class JpaEventEmitter implements DomainEmitter {
    private final MessageProducer producer;
    private final MessageRaisedEntityMapper mapper;
    private final MessageRaisedRepository repository;

    public JpaEventEmitter(MessageProducer messageProducer, MessageRaisedEntityMapper messageRaisedEntityMapper, MessageRaisedRepository repository) {
        this.producer = messageProducer;
        this.mapper = messageRaisedEntityMapper;
        this.repository = repository;
    }

    @Override
    public void emit(DomainEvent event) {
        throw new UnsupportedOperationException("emit for event");
    }

    @Override
    public void emit(DomainCommand command) {
        CommandRaised raised = new CommandRaised(command);
        repository.put(mapper.mapToEntity(raised));
        producer.produce(raised);
    }
}
