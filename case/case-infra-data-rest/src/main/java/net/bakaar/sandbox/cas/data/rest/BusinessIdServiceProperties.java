package net.bakaar.sandbox.cas.data.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service.business-id")
public class BusinessIdServiceProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
