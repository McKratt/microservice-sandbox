package net.bakaar.sandbox.domain.person.data.jpa.repository;

import net.bakaar.sandbox.domain.person.data.jpa.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonJpaRepository extends CrudRepository<PersonEntity, Long> {
    PersonEntity findByPNumber(long pNumber);
}
