package net.bakaar.sandbox.person.rest.controller;

import net.bakaar.sandbox.person.domain.command.CreatePartnerCommand;
import net.bakaar.sandbox.person.domain.query.ReadPartnerQuery;
import net.bakaar.sandbox.person.domain.repository.PartnerRepository;
import net.bakaar.sandbox.person.domain.service.CreatePartnerUseCase;
import net.bakaar.sandbox.person.rest.dto.CreatePartnerCommandDTO;
import net.bakaar.sandbox.person.rest.dto.PartnerDTO;
import net.bakaar.sandbox.person.rest.mapper.PartnerDomainDtoMapper;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/rest/api/v1/partners")
public class PartnerRestController {
    private final CreatePartnerUseCase service;
    private final PartnerRepository partnerRepository;
    private final PartnerDomainDtoMapper mapper;

    PartnerRestController(CreatePartnerUseCase service,
                          PartnerRepository partnerRepository,
                          PartnerDomainDtoMapper mapper) {
        this.service = service;
        this.partnerRepository = partnerRepository;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> create(@RequestBody CreatePartnerCommandDTO dto) {
        CreatePartnerCommand command = new CreatePartnerCommand(dto.getName(), dto.getForename(), dto.getBirthDate());
        return ResponseEntity.created(URI.create("")).body(mapper.mapToDto(service.createPartner(command)));
    }


    @GetMapping("/{partnerId}")
    @ResponseStatus(HttpStatus.OK)
    public PartnerDTO readAPartner(@PathVariable String partnerId) {
        return mapper.mapToDto(partnerRepository.fetchPartner(new ReadPartnerQuery(PNumber.of(partnerId))));
    }
}
