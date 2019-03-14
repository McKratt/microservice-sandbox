package net.bakaar.sandbox.cas.data.rest;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import net.bakaar.sandbox.cas.data.rest.config.PactSpringTestConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PactSpringTestConfiguration.class})
public class BusinessIdRepositoryPactIT {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("businessNumber-provider", "localhost", 8090, this);
    @Autowired
    private BusinessIdRepositoryAdapter service;

    @Pact(consumer = "case-service")
    public RequestResponsePact createPactContract(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Get New Case Id")
                .path("/bns/rest/api/v1/business-number/case-id")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(PactDslJsonRootValue.uuid())
                .toPact();
    }

    @Test
    @PactVerification
    public void businessIdProvider_should_call_businesIdService() {
        //Given
        //When
        //Then
        assertThat(service.generateId()).matches(Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }
}