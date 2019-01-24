package net.bakaar.sandbox.cas.data.jpa;

import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pnummer;
    private String businessId;

    public static CaseEntity fromCase(Case aCase) {
        return new CaseEntity()
                .setPnummer(aCase.getInjured().format())
                .setBusinessId(aCase.getId());
    }

    public String getPnummer() {
        return pnummer;
    }

    public CaseEntity setPnummer(String pnummer) {
        this.pnummer = pnummer;
        return this;
    }

    public String getBusinessId() {
        return businessId;
    }

    public CaseEntity setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public Case toCase() {
        return Case.builder()
                .withBusinnessId(this.businessId)
                .withInjured(PNumber.of(this.getPnummer()));
    }

    public Long getId() {
        return id;
    }
}
