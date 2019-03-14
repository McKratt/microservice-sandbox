package net.bakaar.sandbox.cas.rest.dto;

import net.bakaar.sandbox.cas.domain.entity.Case;

import java.time.LocalDate;

public class CaseDTO {

    private PartnerDTO injured;
    private String id;

    public static CaseDTO fromCase(Case aCase) {
        return new CaseDTO().setId(aCase.getId())
                .addPnummerInjured(aCase.getInjured().format());
    }

    public CaseDTO addPnummerInjured(String pNummer) {
        checkIfInjuredNull();
        injured.setPnummer(pNummer);
        return this;
    }

    public PartnerDTO getInjured() {
        return injured;
    }

    public CaseDTO addBirhtDateInjured(LocalDate birthDate) {
        checkIfInjuredNull();
        injured.setBirthDate(birthDate);
        return this;
    }

    public String getId() {
        return id;
    }

    public CaseDTO setId(String id) {
        this.id = id;
        return this;
    }

    private void checkIfInjuredNull() {
        if (injured == null) {
            injured = new PartnerDTO();
        }
    }
}
