package net.bakaar.sandbox.message.spring.rabbitmq;

import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.EventRaised;
import net.bakaar.sandbox.event.MessageProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitmqMessageProducer implements MessageProducer {

    private final RabbitTemplate template;

    public RabbitmqMessageProducer(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void produce(EventRaised event) {

    }

    @Override
    public void produce(CommandRaised commandRaised) {
        template.convertAndSend("spring-boot-exchange", "foo.bar.baz", "Hello from RabbitMQ!");
    }
}
