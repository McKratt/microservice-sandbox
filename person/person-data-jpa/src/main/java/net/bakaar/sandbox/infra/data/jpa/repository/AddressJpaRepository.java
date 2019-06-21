package net.bakaar.sandbox.infra.data.jpa.repository;

import net.bakaar.sandbox.infra.data.jpa.entity.PersonalAddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressJpaRepository extends CrudRepository<PersonalAddressEntity, Long> {

    Optional<PersonalAddressEntity> findByNumber(long number);
}
