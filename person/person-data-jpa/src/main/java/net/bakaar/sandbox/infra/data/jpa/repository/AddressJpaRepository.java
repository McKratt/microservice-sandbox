package net.bakaar.sandbox.infra.data.jpa.repository;

import net.bakaar.sandbox.infra.data.jpa.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressJpaRepository extends CrudRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByAddressLine(String addressLine);

    Optional<AddressEntity> findByNumber(long number);
}
