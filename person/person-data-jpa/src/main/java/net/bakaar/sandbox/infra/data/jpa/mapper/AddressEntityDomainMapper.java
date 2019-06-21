package net.bakaar.sandbox.infra.data.jpa.mapper;

import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonalAddressEntity;
import net.bakaar.sandbox.infra.data.jpa.repository.AddressJpaRepository;

public class AddressEntityDomainMapper {
    private final AddressJpaRepository repository;

    public AddressEntityDomainMapper(AddressJpaRepository repository) {
        this.repository = repository;
    }

    public PersonalAddressEntity mapToEntity(PersonalAddress address) {
        return repository.findByNumber(address.getId().getValue())
                .orElse(createNewEntity(address));
//        return createNewEntity(address);
    }

    private PersonalAddressEntity createNewEntity(PersonalAddress address) {
        PersonalAddressEntity entity = new PersonalAddressEntity();
        entity.setAddressLine(address.getAddress());
        entity.setNumber(address.getId().getValue());
        return entity;
    }

    public PersonalAddress mapToDomain(PersonalAddressEntity entity) {
        return PersonalAddress.of(AddressNumber.of(entity.getNumber()), entity.getAddressLine());
    }
}
