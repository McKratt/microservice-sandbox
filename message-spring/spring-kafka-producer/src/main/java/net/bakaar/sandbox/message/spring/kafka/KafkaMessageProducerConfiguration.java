package net.bakaar.sandbox.message.spring.kafka;

import net.bakaar.sandbox.event.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaMessageProducerConfiguration {

    @Bean
    MessageProducer producer(KafkaTemplate template) {
        return new KafkaMessageProducer(template);
    }
}
