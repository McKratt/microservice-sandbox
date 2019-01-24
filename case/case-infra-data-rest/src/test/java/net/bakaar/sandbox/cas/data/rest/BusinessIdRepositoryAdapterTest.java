package net.bakaar.sandbox.cas.data.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BusinessIdRepositoryAdapterTest {

    @Mock
    private BusinessIdServiceProperties properties;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BusinessIdRepositoryAdapter service;

    @Test
    public void genrateId_should_call_the_bns_service() {
        //Given
        String baseUrl = "http://example.com/bns";
        given(properties.getUrl()).willReturn(baseUrl);
        String completeUrl = baseUrl + "/rest/api/v1/business-number/case-id";
        given(restTemplate.getForObject(completeUrl, UUID.class)).willReturn(UUID.randomUUID());
        //When
        String response = service.generateId();
        //Then
        verify(restTemplate).getForObject(completeUrl, UUID.class);
        assertThat(response).isNotNull();

    }
}