package net.bakaar.sandbox.domain.person;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitParamsRunner.class)
public class PostalLineTest {

    @Test
    public void of_should_set_the_value() {
        //Given
        String line = "This is a line";
        //When
        PostalLine createdLine = PostalLine.of(line);
        //Then
        assertThat(createdLine.getLine()).isEqualTo(line);
    }

    @Test
    @Parameters(method = "getParamsForException")
    public void of_should_throw_exception(String value) {
        //Given
        //When
        Throwable thrown = catchThrowable(() -> PostalLine.of(value));
        //Then
        assertThat(thrown).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }

    private Object getParamsForException() {
        return new Object[]{null, "", " ", "This is a very long line than exceed 30 characters allowed"};
    }
}