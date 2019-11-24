package net.bakaar.sandbox.message.spring.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.EventRaised;
import net.bakaar.sandbox.event.MessageProducer;
import net.bakaar.sandbox.shared.domain.exception.TechnicalException;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class KafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper jsonSerializer;

    KafkaMessageProducer(KafkaTemplate template, ObjectMapper jsonSerializer) {
        this.template = template;
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public void produce(EventRaised event) {
        LOG.debug("SENDING MESSAGE");
        try {
            template.send(template.getDefaultTopic(), event.getClass().getCanonicalName(), jsonSerializer.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            LOG.error("ERROR during json serialization");
            throw new TechnicalException(e);
        }
    }

    @Override
    public void produce(CommandRaised commandRaised) {
        throw new UnsupportedOperationException("send command");
    }
}
