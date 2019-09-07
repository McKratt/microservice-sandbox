package net.bakaar.sandbox.spring.event;

import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.MessageRaisedRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SpringDataMessageRaisedRepositoryTest {

    @Test
    void put_should_save_the_entity() {
        // Given
        MessageRaisedJpaRepository jpaRepository = mock(MessageRaisedJpaRepository.class);
        MessageRaisedEntity entity = mock(MessageRaisedEntity.class);
        MessageRaisedRepository repository = new SpringDataMessageRaisedRepository(jpaRepository);
        // When
        repository.put(entity);
        // Then
        verify(jpaRepository).save(entity);
    }
}