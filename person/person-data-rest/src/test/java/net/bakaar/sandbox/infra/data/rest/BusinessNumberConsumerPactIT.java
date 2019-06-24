package net.bakaar.sandbox.infra.data.rest;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.infra.data.rest.configuration.BusinessNumberServiceProperties;
import net.bakaar.sandbox.infra.data.rest.configuration.PersonDataRestConfiguration;
import net.bakaar.sandbox.infra.data.rest.test.PactTestConfiguration;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PactTestConfiguration.class, PersonDataRestConfiguration.class})
@Ignore("Until reactivate BNS endpoibnt")
public class BusinessNumberConsumerPactIT {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("businessNumber-provider", "localhost", 8090, this);

    @Autowired
    private BusinessNumberRepository repository;

    @MockBean
    private BusinessNumberServiceProperties properties;


    @Before
    public void initMock() {
        given(properties.getUrl()).willReturn("http://localhost:8090/bns");
    }

    @Pact(consumer = "person-consumer")
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
    @PactVerification
    public void control_get_person_businessnumber() {
        //Given
        //When
        PNumber pnumber = repository.fetchNextPNumber();
        //Then
        assertThat(pnumber).isNotNull().extracting(PNumber::getValue).isEqualTo(54637289L);
    }

}