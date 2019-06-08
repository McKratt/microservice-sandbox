package net.bakaar.sandbox.person.infra.service.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressCommand {
    String address;
    boolean main;

    public static CreateAddressCommand of(String address) {
        return new CreateAddressCommand(address, false);
    }

    public static CreateAddressCommand mainOf(String address) {
        return new CreateAddressCommand(address, true);
    }
}
