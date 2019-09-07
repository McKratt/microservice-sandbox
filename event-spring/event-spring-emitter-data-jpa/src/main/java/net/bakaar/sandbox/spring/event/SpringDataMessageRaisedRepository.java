package net.bakaar.sandbox.spring.event;

import net.bakaar.sandbox.event.MessageRaisedEntity;
import net.bakaar.sandbox.event.MessageRaisedRepository;

class SpringDataMessageRaisedRepository implements MessageRaisedRepository {

    private final MessageRaisedJpaRepository repository;

    SpringDataMessageRaisedRepository(MessageRaisedJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void put(MessageRaisedEntity entity) {
        repository.save(entity);
    }
}
