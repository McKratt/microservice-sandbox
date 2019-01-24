package net.bakaar.sandbox.cas.data.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CaseDataRestConfigurationTest {

    private CaseDataRestConfiguration configuration = new CaseDataRestConfiguration();

    @Test
    public void objectMapper_should_configure_correctly() {
        //Given
        //When
        ObjectMapper mapper = configuration.objectMapper();
        //Then
        Assertions.assertThat(mapper).isNotNull();
        assertThat((Set<Object>) ReflectionTestUtils.getField(mapper, "_registeredModuleTypes")).contains(new JavaTimeModule().getTypeId());
    }

    @Test
    public void businessIdProvider_should_configure_provider() {
        //Given
        BusinessIdServiceProperties properties = mock(BusinessIdServiceProperties.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        //When
        BusinessIdRepository provider = configuration.businessIdProvider(properties, restTemplate);
        //Then
        Assertions.assertThat(provider).isInstanceOf(BusinessIdRepositoryAdapter.class);
        Assertions.assertThat(ReflectionTestUtils.getField(provider, "properties")).isEqualTo(properties);
        Assertions.assertThat(ReflectionTestUtils.getField(provider, "restTemplate")).isEqualTo(restTemplate);
    }
}
