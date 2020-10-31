package net.bakaar.sandbox.message.spring.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.event.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration(proxyBeanMethods = false)
public class KafkaMessageProducerConfiguration {

    @Bean
    MessageProducer producer(KafkaTemplate template, ObjectMapper jsonSerializer) {
        return new KafkaMessageProducer(template, jsonSerializer);
    }
}
