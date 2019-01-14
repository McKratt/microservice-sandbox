package net.bakaar.sandbox.person.domain.service;

import net.bakaar.sandbox.event.domain.EventStore;
import net.bakaar.sandbox.person.domain.entity.Partner;
import net.bakaar.sandbox.person.domain.event.PartnerCreated;
import net.bakaar.sandbox.person.domain.repository.BusinessNumberRepository;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.time.LocalDate;

public class PersonDomaineService implements CreatePartnerUseCase {

    private final PartnerRepository store;
    private final BusinessNumberRepository businessNumberRepository;
    private final EventStore eventStore;

    public PersonDomaineService(PartnerRepository store, BusinessNumberRepository businessNumberRepository, EventStore eventStore) {
        this.store = store;
        this.businessNumberRepository = businessNumberRepository;
        this.eventStore = eventStore;
    }

    @Override
    public Partner createPartner(String name, String forename, LocalDate birthDate) {
        PNumber id = businessNumberRepository.createPartnerNumber();
        Partner created = store.push(Partner.of(id, name, forename, birthDate));
        eventStore.store(PartnerCreated.of(created.getId()));
        return created;
    }
}
