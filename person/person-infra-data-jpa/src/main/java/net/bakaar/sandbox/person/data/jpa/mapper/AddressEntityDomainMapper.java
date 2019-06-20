package net.bakaar.sandbox.person.data.jpa.mapper;

import net.bakaar.sandbox.person.data.jpa.entity.AddressEntity;
import net.bakaar.sandbox.person.data.jpa.entity.PersonAddressesEntity;
import net.bakaar.sandbox.person.domain.entity.Address;
import net.bakaar.sandbox.person.domain.vo.AddressNumber;

public class AddressEntityDomainMapper {
//    private final AddressJpaRepository repository;

//    public AddressEntityDomainMapper(AddressJpaRepository repository) {
//        this.repository = repository;
//    }

    public AddressEntity mapToEntity(Address address) {
//        return repository.findByAddressLine(address.getAddress())
//                .orElse(createNewEntity(address));
        return createNewEntity(address);
    }

    private AddressEntity createNewEntity(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setAddressLine(address.getAddress());
        entity.setNumber(address.getId().getValue());
        return entity;
    }

    public Address mapToDomain(PersonAddressesEntity entity) {
        Address address = Address.of(AddressNumber.of(entity.getAddress().getNumber()), entity.getAddress().getAddressLine());
        if (entity.isMain()) {
            address.makeItMain();
        }
        return address;
    }
}
