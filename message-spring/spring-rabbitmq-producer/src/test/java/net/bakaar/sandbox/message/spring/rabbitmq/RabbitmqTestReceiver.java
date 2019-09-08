package net.bakaar.sandbox.message.spring.rabbitmq;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class RabbitmqTestReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        LOG.debug("MESSAGE RECEIVED : {}", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
