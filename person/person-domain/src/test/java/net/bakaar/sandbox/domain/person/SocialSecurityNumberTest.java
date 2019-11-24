package net.bakaar.sandbox.domain.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SocialSecurityNumberTest {

    @Test
    void of_should_store_the_number() {
        // Given
        long number = 7561234567890L;
        // When
        SocialSecurityNumber ssn = SocialSecurityNumber.of(number);
        // Then
        assertThat(ssn).isNotNull().extracting("number").isEqualTo(number);
    }

    @Test
    void format_should_show_number_with_correct_format() {
        // Given
        SocialSecurityNumber ssn = SocialSecurityNumber.of(7561234567890L);
        // When
        String returned = ssn.format();
        // Then
        assertThat(returned).isEqualTo("756.1234.5678.90");
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 12345678909876L})
    void constructor_should_throw_error_when_number_longer_than_13_digits(long number) {
        // Given
        // When
        Throwable thrown = catchThrowable(() -> SocialSecurityNumber.of(number));
        // Then
        assertThat(thrown).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void value_should_return_number() {
        // Given
        long number = 7561234567890L;
        SocialSecurityNumber ssn = SocialSecurityNumber.of(number);
        // When
        Long returned = ssn.value();
        // Then
        assertThat(returned).isEqualTo(number);
    }
}