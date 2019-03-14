package net.bakaar.sandbox.cas.rest.error;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RestResponseEntityErrorHandlerTest {

    private RestResponseEntityErrorHandler handler = new RestResponseEntityErrorHandler();

    @Test
    public void handleInternalError_should_return_stacktrace() {
        //Given
        String errorText = "This is an error !";
        RuntimeException exception = new RuntimeException(errorText);
        WebRequest request = mock(WebRequest.class);
        //When
        ResponseEntity response = handler.handleInternalError(exception, request);
        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat((String) response.getBody()).contains(errorText);
    }
}