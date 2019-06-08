package net.bakaar.sandbox.messaging.producer;

import net.bakaar.sandbox.messaging.common.Message;

public interface MessageProducer {

    void produce(Message messsage);
}
