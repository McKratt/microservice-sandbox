package net.bakaar.sandbox.domain.person.data.rest;

import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BusinessNumberRepositoryAdapterTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final String baseUrl = "Http://TestUrl.net";
    private final String completeUrlPattern = "%s/rest/api/v1/business-number/%s";
    private final BusinessNumberServiceProperties properties = mock(BusinessNumberServiceProperties.class);
    private final BusinessNumberRepositoryAdapter client = new BusinessNumberRepositoryAdapter(properties, restTemplate);

    @Before
    public void setUpMocks() {
        given(properties.getUrl()).willReturn(baseUrl);
    }

    @Test
    public void fetchNextPNumber_should_call_rest_endpoint() {
        //Given
        long result = 98765432L;
        String completeUrl = String.format(completeUrlPattern, baseUrl, "partner-id");
        given(restTemplate.getForObject(completeUrl, Long.class)).willReturn(result);
        //When
        PNumber number = client.fetchNextPNumber();
        //Then
        verify(restTemplate).getForObject(completeUrl, Long.class);
        verify(properties).getUrl();
        assertThat(number.getValue()).isEqualTo(result);
    }

    @Test
    public void fetchNextAddressNumber_should_call_endpoint() {
        //Given
        long result = 987654325L;
        String completeUrl = String.format(completeUrlPattern, baseUrl, "address-id");
        given(restTemplate.getForObject(completeUrl, Long.class)).willReturn(result);
        //When
        AddressNumber number = client.fetchNextAddressNumber();
        //Then
        verify(restTemplate).getForObject(completeUrl, Long.class);
        verify(properties).getUrl();
        assertThat(number.getValue()).isEqualTo(result);
    }
}