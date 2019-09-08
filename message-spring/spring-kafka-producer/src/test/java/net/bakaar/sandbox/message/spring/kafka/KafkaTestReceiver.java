package net.bakaar.sandbox.message.spring.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Getter
public class KafkaTestReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    private String receivedMessage;

    @KafkaListener(topics = KafkaMessageProducerIT.TEST_TOPIC)
    public void receiveMessage(String message) {
        receivedMessage = message;
        LOG.debug("RECEIVED KAFKA MESSAGE : {}", message);
        latch.countDown();
    }
}
