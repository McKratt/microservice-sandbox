package net.bakaar.sandbox.message.spring.rabbitmq;

import net.bakaar.sandbox.event.CommandRaised;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = ITRabbitmqConfiguration.class, initializers = RabbitmqMessageProducerIT.Initializer.class)
class RabbitmqMessageProducerIT {

    @Container
    private static final RabbitMQContainer queue = new RabbitMQContainer();

    @Autowired
    private RabbitmqTestReceiver receiver;

    @Autowired
    private RabbitmqMessageProducer producer;

    @Test
    void producer_should_send_message_to_amqp() throws InterruptedException {
        // Given
        producer.produce(mock(CommandRaised.class));
        // When
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        // Then
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.rabbitmq.port=" + queue.getAmqpPort(),
                    "spring.rabbitmq.password=" + queue.getAdminPassword(),
                    "spring.rabbitmq.username=" + queue.getAdminUsername()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}

