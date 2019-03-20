package net.bakaar.sandbox.event.domain.springdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.event.domain.DomainEvent;
import net.bakaar.sandbox.shared.domain.exception.TechnicalException;

import java.time.Instant;

public class DBEventRaisedFactory {

    private final ObjectMapper mapper;

    public DBEventRaisedFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public DBEventRaised fromEvent(DomainEvent event) {
        try {
            return new DBEventRaised(mapper.writeValueAsString(event)).raisedAt(Instant.now());
        } catch (JsonProcessingException e) {
            throw new TechnicalException(e);
        }
    }
}
