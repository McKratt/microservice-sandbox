package net.bakaar.sandbox.message.spring.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration(proxyBeanMethods = false)
@Import(KafkaMessageProducerConfiguration.class)
@EnableAutoConfiguration
public class ITKafkaMessageProducerConfiguration {

    @Bean
    KafkaTestReceiver receiver() {
        return new KafkaTestReceiver();
    }

    @Bean
    ObjectMapper jsonSerializer() {
        return new ObjectMapper();
    }
}
