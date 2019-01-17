package net.bakaar.sandbox.person.rest.controller;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.person.rest.repository.PartnerReadStore;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/rest/api/v1/partners")
public class PartnerRestController {
    private final CreatePartnerUseCase service;
    private final PartnerReadStore readStore;
    private final PartnerDomainDtoMapper mapper;

    public PartnerRestController(@Qualifier("createPartnerApplicationService") CreatePartnerUseCase service,
                                 @Qualifier("readStoreApplicationService") PartnerReadStore readStore,
                                 PartnerDomainDtoMapper mapper) {
        this.service = service;
        this.readStore = readStore;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> create(@RequestBody PartnerDTO partner) {
        CreatePartnerCommand command = new CreatePartnerCommand(partner.getName(), partner.getForename(), partner.getBirthDate());
        return ResponseEntity.created(URI.create("")).body(mapper.mapToDto(service.createPartner(command)));
    }


    @GetMapping("/{partnerId}")
    @ResponseStatus(HttpStatus.OK)
    public PartnerDTO readAPartner(@PathVariable String partnerId) {
        return readStore.fetchPartnerById(PNumber.of(partnerId));
    }
}
