package net.bakaar.sandbox.message.spring.kafka;

import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.EventRaised;
import net.bakaar.sandbox.event.MessageProducer;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate template;

    public KafkaMessageProducer(KafkaTemplate template) {
        this.template = template;
    }

    @Override
    public void produce(EventRaised event) {

        template.send(template.getDefaultTopic(), "Kafka Message");
    }

    @Override
    public void produce(CommandRaised commandRaised) {

    }
}
