package net.bakaar.sandbox.domain.person.address;

import net.bakaar.sandbox.domain.shared.AddressNumber;

public interface AddressRepository {
    Address fetchAddress(AddressNumber mainAddressNumber);
}
