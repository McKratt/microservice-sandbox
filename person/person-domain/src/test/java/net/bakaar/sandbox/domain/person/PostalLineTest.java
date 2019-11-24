package net.bakaar.sandbox.domain.person;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PostalLineTest {

    @Test
    void of_should_set_the_value() {
        //Given
        String line = "This is a line";
        //When
        PostalLine createdLine = PostalLine.of(line);
        //Then
        assertThat(createdLine.getLine()).isEqualTo(line);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "This is a very long line than exceed 30 characters allowed"})
    @NullSource
    void of_should_throw_exception(String value) {
        //Given
        //When
        Throwable thrown = catchThrowable(() -> PostalLine.of(value));
        //Then
        assertThat(thrown).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
