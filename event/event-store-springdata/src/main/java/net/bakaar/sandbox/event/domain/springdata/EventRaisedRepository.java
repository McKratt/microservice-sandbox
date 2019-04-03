package net.bakaar.sandbox.event.domain.springdata;

import org.springframework.data.repository.CrudRepository;

public interface EventRaisedRepository extends CrudRepository<DBEventRaised, Long> {
}
