package net.bakaar.sandbox.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartnerDTO {

    private String id;
    private String name;
    private String forename;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
}
