package net.bakaar.sandbox.businessnumber.endpoint;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessNumberEndpointTest {

    private final BusinessNumberEndpoint endpoint = new BusinessNumberEndpoint();

    @Test
    public void createCaseId_should_return_200_and_a_UUID() {
        //Given
        //When
        ResponseEntity response = endpoint.createCaseId();
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(UUID.class);
    }

    @Test
    public void createPartnerId_should_return_200_and_partnerId() {
        //Given
        //When
        ResponseEntity response = endpoint.createPersonId();
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isInstanceOf(Long.class).is(new Condition<>(aLong -> (Long) aLong > 0, "Should be greater than 0"));
    }
}