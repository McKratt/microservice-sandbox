package net.bakaar.sandbox.person.data.rest;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import net.bakaar.sandbox.person.data.rest.test.PactTestConfiguration;
import net.bakaar.sandbox.person.infra.service.BusinessNumberRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Before;
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
public class BusinessNumberConsumerPactIT {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("businessNumber-provider", "localhost", 8090, this);

    @Autowired
    private BusinessNumberRepository store;

    @MockBean
    private BusinessNumberServiceProperties properties;


    @Before
    public void initMock() {
        given(properties.getUrl()).willReturn("http://localhost:8090/bns");
    }

    @Pact(consumer = "person-consumer")
    public RequestResponsePact createPactForPartnerBusinessNumber(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .uponReceiving("Ask For Partner Business Number")
                .path("/bns/rest/api/v1/business-number/partner-id")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(PactDslRootValue.stringMatcher("[0-9]{8}", "54637289"))
                .toPact();
    }

    @Test
    @PactVerification
    public void control_get_partner_businessnumber() {
        //Given
        //When
        PNumber pnumber = store.fetchNextPNumber();
        //Then
        assertThat(pnumber).isNotNull().extracting(PNumber::getValue).isEqualTo(54637289);
    }

}