package net.bakaar.sandbox.message.spring.kafka;

import net.bakaar.sandbox.event.EventRaised;
import net.bakaar.sandbox.event.MessageProducer;
import net.bakaar.sandbox.message.spring.kafka.test.util.TestDomaineEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("With the new version Kafka connector don't create automatically the topice. To investigate !")
@SpringBootTest(classes = ITKafkaMessageProducerConfiguration.class)
@Testcontainers
@ContextConfiguration(initializers = KafkaMessageProducerIT.Initializer.class)
class KafkaMessageProducerIT {

    static final String TEST_TOPIC = "test-topic";

    @Container
    private static final KafkaContainer queue = new KafkaContainer();

    @Autowired
    MessageProducer producer;

    @Autowired
    KafkaTestReceiver receiver;

    @Test
    void messageProducer_should_send_message_to_kafka() throws InterruptedException {
        // Given
        EventRaised event = new EventRaised(new TestDomaineEvent());
        // When
        producer.produce(event);
        receiver.getLatch().await(100000, TimeUnit.MILLISECONDS);
        // Then
        assertThat(receiver.getReceivedMessage()).isNotNull().contains(TestDomaineEvent.DATA);
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.kafka.bootstrap-servers=" + queue.getBootstrapServers(),
                    "spring.kafka.consumer.group-id=test",
                    "spring.kafka.consumer.auto-offset-reset=earliest",
                    "spring.kafka.template.default-topic=" + TEST_TOPIC
            ).applyTo(applicationContext.getEnvironment());
        }
    }

}
