package net.bakaar.sandbox.event.domain;

import java.time.LocalDateTime;

public interface DomainEvent {

    LocalDateTime raisedAt();
}
