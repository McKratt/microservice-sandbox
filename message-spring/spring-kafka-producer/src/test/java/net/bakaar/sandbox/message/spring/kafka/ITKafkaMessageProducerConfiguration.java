package net.bakaar.sandbox.message.spring.kafka;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(KafkaMessageProducerConfiguration.class)
@EnableAutoConfiguration
public class ITKafkaMessageProducerConfiguration {

    @Bean
    KafkaTestReceiver receiver() {
        return new KafkaTestReceiver();
    }
}
