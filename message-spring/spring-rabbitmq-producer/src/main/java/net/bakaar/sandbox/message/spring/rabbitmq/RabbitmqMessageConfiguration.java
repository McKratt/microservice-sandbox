package net.bakaar.sandbox.message.spring.rabbitmq;

import net.bakaar.sandbox.event.MessageProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqMessageConfiguration {

    @Bean
    MessageProducer producer(RabbitTemplate template) {
        return new RabbitmqMessageProducer(template);
    }
}
