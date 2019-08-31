package net.bakaar.sandbox.event;

public interface MessageProducer {

    void produce(EventRaised event);
}
