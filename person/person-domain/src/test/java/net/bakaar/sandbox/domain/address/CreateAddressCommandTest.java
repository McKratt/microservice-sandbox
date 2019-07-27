package net.bakaar.sandbox.domain.address;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAddressCommandTest {

    @Test
    public void of_should_initialize_correctly() {
        //Given
        String address = "my AddressNumber";
        //When
        CreateAddressCommand command = CreateAddressCommand.of(address);
        //Then
        assertThat(command.getAddress()).isSameAs(address);
        assertThat(command.isMain()).isFalse();
    }
}