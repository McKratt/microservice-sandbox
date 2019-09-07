package net.bakaar.sandbox.spring.event;

import net.bakaar.sandbox.event.MessageRaisedEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface MessageRaisedJpaRepository extends CrudRepository<MessageRaisedEntity, UUID> {
}
