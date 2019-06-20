package net.bakaar.sandbox.rest;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import net.bakaar.sandbox.bootstrap.PersonApplication;
import net.bakaar.sandbox.infra.data.jpa.entity.PersonEntity;
import net.bakaar.sandbox.infra.data.jpa.repository.PersonJpaRepository;
import net.bakaar.sandbox.rest.test.config.PactTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = {PactTestConfiguration.class, PersonApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("partner-api")
@PactFolder("pacts")
public class PersonAPIProviderPactIT {

    @TestTarget
    public final Target target = new SpringBootHttpTarget();
    @Autowired
    private PersonJpaRepository repository;

    @State("Exist a Person")
    public void createAPartner() {

        PersonEntity entity = new PersonEntity();
        entity.setPNumber(12345678L);
        entity.setBirthDate(LocalDate.of(1879, 3, 14));
        entity.setName("Einstein");
        entity.setForename("Albert");
        repository.save(entity);
    }

}
