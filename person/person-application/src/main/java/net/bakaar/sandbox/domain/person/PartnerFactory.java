package net.bakaar.sandbox.domain.person;

import net.bakaar.sandbox.domain.person.address.Address;
import net.bakaar.sandbox.domain.person.address.AddressRepository;
import net.bakaar.sandbox.domain.person.number.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class PartnerFactory {
    private final BusinessNumberRepository businessNumberRepository;
    private final AddressRepository addressRepository;


    public PartnerFactory(BusinessNumberRepository businessNumberRepository, AddressRepository addressRepository) {
        this.businessNumberRepository = businessNumberRepository;
        this.addressRepository = addressRepository;
    }

    Person createPartner(CreatePersonCommand command) {
        PNumber number = businessNumberRepository.fetchNextPNumber();
        Address address = addressRepository.fetchAddress(command.getMainAddressNumber());
        return Person.of(command.getName(), command.getForename(), command.getBirthDate(), address).withId(number).build();
    }
}
