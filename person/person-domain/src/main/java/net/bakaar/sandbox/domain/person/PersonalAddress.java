package net.bakaar.sandbox.domain.person;

import lombok.Getter;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import org.apache.commons.lang3.StringUtils;

@Getter
public class PersonalAddress {
    private final AddressNumber id;
    private final String address;

    private PersonalAddress(AddressNumber id, String address) {
        if (id == null) {
            throw new IllegalArgumentException("Id should not be null !");
        }
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("The address should not be empty nor null !");
        }
        this.id = id;
        this.address = address;
    }

    public static PersonalAddress of(AddressNumber id, String address) {
        return new PersonalAddress(id, address);
    }
}
