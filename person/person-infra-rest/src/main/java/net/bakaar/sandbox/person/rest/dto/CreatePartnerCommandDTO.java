package net.bakaar.sandbox.person.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePartnerCommandDTO {
    private String name;
    private String forename;
    private LocalDate birthDate;
}
