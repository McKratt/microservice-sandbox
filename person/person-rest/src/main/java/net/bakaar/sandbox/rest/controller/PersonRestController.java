package net.bakaar.sandbox.rest.controller;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.rest.dto.CreatePersonCommandDTO;
import net.bakaar.sandbox.rest.dto.PersonDTO;
import net.bakaar.sandbox.rest.mapper.PersonDtoMapper;
import net.bakaar.sandbox.rest.mapper.PersonalAddressDtoMapper;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/api/v1")
public class PersonRestController {
    private final PersonApplicationService service;
    private final PersonDtoMapper mapper;
    private final PersonalAddressDtoMapper personalAddressDtoMApper;

    PersonRestController(PersonApplicationService service,
                         PersonDtoMapper mapper, PersonalAddressDtoMapper personalAddressDtoMapper) {
        this.service = service;
        this.mapper = mapper;
        this.personalAddressDtoMApper = personalAddressDtoMapper;
    }

    @GetMapping("/ping")
    public ResponseEntity ping() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/persons")
    public ResponseEntity<PersonDTO> create(@RequestBody CreatePersonCommandDTO dto) {
        CreatePersonCommand command = CreatePersonCommand
                .of(dto.getName(), dto.getForename(), dto.getBirthDate(), personalAddressDtoMApper.mapToDomain(dto.getMainAddress()));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(service.createPerson(command)));
    }


    @GetMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO readPerson(@PathVariable String personId) {
        return mapper.mapToDto(service.readPerson(PNumber.of(personId)));
    }
}
