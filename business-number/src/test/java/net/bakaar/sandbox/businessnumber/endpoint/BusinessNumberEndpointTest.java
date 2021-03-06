package net.bakaar.sandbox.businessnumber.endpoint;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessNumberEndpointTest {

    private final BusinessNumberEndpoint endpoint = new BusinessNumberEndpoint();

    @Test
    void createPartnerId_should_return_200_and_partnerId() {
        //Given
        //When
        ResponseEntity response = endpoint.createPersonId();
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isInstanceOf(Long.class).is(new Condition<>(aLong -> (Long) aLong > 0, "Should be greater than 0"));
    }
}