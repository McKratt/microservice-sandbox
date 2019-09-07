package net.bakaar.sandbox.event;

public interface MessageProducer {

    void produce(EventRaised event);

    void produce(CommandRaised commandRaised);
}
