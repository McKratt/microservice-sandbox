package net.bakaar.sandbox.cas.rest.dto;

import java.time.LocalDate;

public class PartnerDTO {

    private String pnummer;
    private LocalDate birthDate;

    public String getPnummer() {
        return pnummer;
    }

    public void setPnummer(String pnummer) {
        this.pnummer = pnummer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
