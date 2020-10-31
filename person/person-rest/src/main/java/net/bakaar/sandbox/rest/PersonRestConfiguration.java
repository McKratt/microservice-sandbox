package net.bakaar.sandbox.rest;

import net.bakaar.sandbox.rest.controller.PersonRestController;
import net.bakaar.sandbox.rest.mapper.PersonDtoMapper;
import net.bakaar.sandbox.rest.mapper.PersonalAddressDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = PersonRestController.class)
public class PersonRestConfiguration {

    @Bean
    public PersonDtoMapper partnerDomainDtoMapper() {
        return new PersonDtoMapper();
    }

    @Bean
    public PersonalAddressDtoMapper personalAddressDtoMapper() {
        return new PersonalAddressDtoMapper();
    }
}
