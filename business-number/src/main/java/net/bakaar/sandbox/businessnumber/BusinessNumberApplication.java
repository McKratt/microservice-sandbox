package net.bakaar.sandbox.businessnumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class BusinessNumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessNumberApplication.class, args);
    }
}
