package net.bakaar.sandbox.cas.rest;

import net.bakaar.sandbox.cas.rest.controller.CaseResourceController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CaseResourceController.class)
public class CaseRestConfiguration {
}
