package net.bakaar.sandbox.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MessageRaisedEntity {
    private LocalDateTime raisedAt;
    @Id
    private UUID id;
    private String payload;
}
