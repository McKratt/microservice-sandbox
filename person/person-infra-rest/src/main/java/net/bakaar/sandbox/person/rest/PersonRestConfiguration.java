package net.bakaar.sandbox.person.rest;

import net.bakaar.sandbox.person.rest.controller.PartnerRestController;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PartnerRestController.class)
public class PersonRestConfiguration {


    @Bean
    public PartnerDomainDtoMapper partnerDomainDtoMapper() {
        return new PartnerDomainDtoMapper();
    }
}
