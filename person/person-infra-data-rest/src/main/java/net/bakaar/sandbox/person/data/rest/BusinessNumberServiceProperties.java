package net.bakaar.sandbox.person.data.rest;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "net.bakaar.bns")
public class BusinessNumberServiceProperties {

    private String url;

}
