package net.bakaar.sandbox.infra.data.rest;

import au.com.dius.pact.consumer.dsl.PactDslRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.infra.data.rest.configuration.BusinessNumberServiceProperties;
import net.bakaar.sandbox.infra.data.rest.configuration.PersonDataRestConfiguration;
import net.bakaar.sandbox.infra.data.rest.test.PactTestConfiguration;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith({SpringExtension.class, PactConsumerTestExt.class})
@ContextConfiguration(classes = {PactTestConfiguration.class, PersonDataRestConfiguration.class})
@PactTestFor(providerName = "businessNumber-provider", port = "8090")
@Disabled("Until reactivate BNS endpoibnt")
public class BusinessNumberConsumerPactIT {

    @Autowired
    private BusinessNumberRepository repository;

    @MockBean
    private BusinessNumberServiceProperties properties;


    @BeforeEach
    void initMock() {
        given(properties.getUrl()).willReturn("http://localhost:8090/bns");
    }

    @Pact(provider = "businessNumber-provider", consumer = "person-consumer")
    public RequestResponsePact createPactForPersonBusinessNumber(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .uponReceiving("Ask For Person Business Number")
                .path("/bns/rest/api/v1/business-number/person-id")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(PactDslRootValue.integerType(54637289L))
                .toPact();
    }

    @Test
    void control_get_person_businessnumber() {
        //Given
        //When
        PNumber pnumber = repository.fetchNextPNumber();
        //Then
        assertThat(pnumber).isNotNull().extracting(PNumber::getValue).isEqualTo(54637289L);
    }

}