package net.bakaar.sandbox.event;

import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.event.domain.DomainEventEmitter;

public class DirectEventEmitter implements DomainEventEmitter {

    private final MessageProducer messageProducer;

    public DirectEventEmitter(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void emit(DomainEvent event) {
        messageProducer.produce(new EventRaised(event));
    }
}
