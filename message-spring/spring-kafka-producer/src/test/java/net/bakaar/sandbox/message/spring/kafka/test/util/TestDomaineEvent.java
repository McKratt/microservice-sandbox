package net.bakaar.sandbox.message.spring.kafka.test.util;

import lombok.Getter;
import net.bakaar.sandbox.event.domain.DomainEvent;

@Getter
public class TestDomaineEvent implements DomainEvent {
    public final static String DATA = "Some data";
    private final String data = DATA;
}
