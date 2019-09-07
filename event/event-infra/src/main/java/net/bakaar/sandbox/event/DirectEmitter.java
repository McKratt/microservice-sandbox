package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainCommand;
import net.bakaar.sandbox.event.domain.DomainEmitter;
import net.bakaar.sandbox.event.domain.DomainEvent;

/**
 * This emitter doesn't conserve the emitted object
 */
public class DirectEmitter implements DomainEmitter {

    private final MessageProducer messageProducer;

    public DirectEmitter(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void emit(DomainEvent event) {
        messageProducer.produce(new EventRaised(event));
    }

    @Override
    public void emit(DomainCommand command) {
        messageProducer.produce(new CommandRaised(command));
    }
}
