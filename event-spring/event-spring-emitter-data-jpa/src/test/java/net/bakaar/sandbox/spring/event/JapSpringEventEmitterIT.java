package net.bakaar.sandbox.spring.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.event.CommandRaised;
import net.bakaar.sandbox.event.MessageProducer;
import net.bakaar.sandbox.event.domain.DomainCommand;
import net.bakaar.sandbox.event.domain.DomainEmitter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ContextConfiguration(classes = SpringJpaDataEmitterConfiguration.class, initializers = {JapSpringEventEmitterIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE)
@Transactional
@Testcontainers
class JapSpringEventEmitterIT {

    @Autowired
    private MessageRaisedJpaRepository repository;

    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @Autowired
    private DomainEmitter emitter;

    @MockBean
    private MessageProducer producer;

    @MockBean
    private ObjectMapper jsonMapper;

    @Test
    void command_should_be_saved_in_db() throws JsonProcessingException {
        // Given
        DomainCommand command = new TestCommand();
        // When
        TestTransaction.flagForCommit();
        emitter.emit(command);
        TestTransaction.end();
        // Then
        TestTransaction.start();
        assertThat(repository.findAll()).hasSize(1);
        TestTransaction.end();
        verify(producer).produce(any(CommandRaised.class));
        verify(jsonMapper).writeValueAsString(any(DomainCommand.class));
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    private class TestCommand implements DomainCommand {
    }
}
