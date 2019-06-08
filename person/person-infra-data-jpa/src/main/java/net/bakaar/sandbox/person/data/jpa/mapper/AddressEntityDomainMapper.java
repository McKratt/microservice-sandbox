package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.person.data.jpa.repository.AddressJpaRepository;
import net.bakaar.sandbox.person.domain.entity.Address;

public class AddressEntityDomainMapper {
    private final AddressJpaRepository repository;

    public AddressEntityDomainMapper(AddressJpaRepository repository) {
        this.repository = repository;
    }

    public AddressEntity mapToEntity(Address address) {
        return repository.findByAddressLine(address.getAddress())
                .orElse(createNewEntity(address));
    }

    private AddressEntity createNewEntity(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setAddressLine(address.getAddress());
        entity.setNumber(address.getId().getValue());
        return entity;
    }
}
