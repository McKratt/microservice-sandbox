package net.bakaar.sandbox.message.spring.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Getter
public class KafkaTestReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    private String receivedMessage;

    @KafkaListener(topics = KafkaMessageProducerIT.TEST_TOPIC)
    public void receiveMessage(@Payload String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        receivedMessage = message;
        LOG.debug("RECEIVED KAFKA MESSAGE : {}, with KEY : {}", message, key);
        latch.countDown();
    }
}
