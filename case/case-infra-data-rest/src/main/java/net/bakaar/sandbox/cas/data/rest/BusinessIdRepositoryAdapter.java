package net.bakaar.sandbox.cas.data.rest;

import net.bakaar.sandbox.cas.domain.repository.BusinessIdRepository;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class BusinessIdRepositoryAdapter implements BusinessIdRepository {

    private BusinessIdServiceProperties properties;
    private RestTemplate restTemplate;

    private static final String API_URL = "rest/api/v1/business-number";

    public BusinessIdRepositoryAdapter(BusinessIdServiceProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public String generateId() {
        String url = String.format("%s/%s/case-id", properties.getUrl(), API_URL);
        return restTemplate.getForObject(url, UUID.class).toString();
    }
}
