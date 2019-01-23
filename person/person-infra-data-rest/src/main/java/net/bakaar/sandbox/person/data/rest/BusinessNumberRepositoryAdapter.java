package net.bakaar.sandbox.person.data.rest;

import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.web.client.RestTemplate;

public class BusinessNumberRepositoryAdapter implements BusinessNumberRepository {
    private final BusinessNumberServiceProperties properties;
    private final RestTemplate restTemplate;

    private static final String API_URL = "rest/api/v1/business-number";

    public BusinessNumberRepositoryAdapter(BusinessNumberServiceProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public PNumber createPartnerNumber() {
        String url = String.format("%s/%s/partner-id", properties.getUrl(), API_URL);
        return PNumber.of(restTemplate.getForObject(url, Long.class));
    }
}
