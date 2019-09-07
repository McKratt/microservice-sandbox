package net.bakaar.sandbox.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MessageRaisedEntity {
    private LocalDateTime raisedAt;
    private UUID id;
    private String payload;
}
