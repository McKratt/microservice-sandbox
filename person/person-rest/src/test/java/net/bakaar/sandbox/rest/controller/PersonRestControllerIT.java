package net.bakaar.sandbox.rest.controller;

import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.domain.person.PersonalAddress;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.rest.PersonRestConfiguration;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PersonRestConfiguration.class)
@WebMvcTest
@AutoConfigureMockMvc
class PersonRestControllerIT {

    private final String baseUrl = "/rest/api/v1/persons";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonApplicationService service;

    @Test
    void create_should_return_a_complete_person() throws Exception {
        String pid = "P34567890";
        String name = "MyName";
        String forename = "MyForename";
        String addressLine = "23, rue du Bac";
        PersonalAddress mainAddress = PersonalAddress.of(AddressNumber.of(7568463725L), addressLine);
        given(service.createPerson(any(CreatePersonCommand.class))).willAnswer(invocation -> {
            CreatePersonCommand command = invocation.getArgument(0);
            return Person
                    .of(command.getName(), command.getForename(), command.getBirthDate(), command.getMainAddress())
                    .withId(PNumber.of(pid))
                    .build();
        });
        mockMvc
                .perform(post(baseUrl)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(String.format("{" +
                                "  \"name\": \"%s\"," +
                                "  \"forename\": \"%s\"," +
                                "  \"birthDate\": \"12.03.1945\"," +
                                "  \"mainAddress\": {" +
                                "    \"id\": \"A756421345\"," +
                                "    \"address\": \"%s\"" +
                                "  }" +
                                "}", name, forename, addressLine)
                        )
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pid))
        ;
        ArgumentCaptor<CreatePersonCommand> captor = ArgumentCaptor.forClass(CreatePersonCommand.class);
        verify(service).createPerson(captor.capture());
        CreatePersonCommand captured = captor.getValue();
        assertThat(captured.getForename()).isEqualTo(forename);
        assertThat(captured.getName()).isEqualTo(name);
        assertThat(captured.getMainAddress()).isEqualToComparingFieldByField(mainAddress);
    }

    @Test
    void readAPerson_should_answer_OK() throws Exception {
        long id = 56743245L;
        PNumber pNumber = PNumber.of(id);
        String name = "MyName";
        String forename = "MyForename";
        AddressNumber addressNumber = AddressNumber.of(7568463725L);
        String addressLine = "23, rue du Bac";
        PersonalAddress mainAddress = PersonalAddress.of(addressNumber, addressLine);
        Person returnedDto = Person.of(name, forename, LocalDate.now(), mainAddress).withId(pNumber).build();
        given(service.readPerson(pNumber)).willReturn(returnedDto);
        mockMvc.perform(get(baseUrl + "/" + pNumber.format())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.forename").value(forename))
                .andExpect(jsonPath("$.id").value(pNumber.format()))
                .andExpect(jsonPath("$.mainAddress.id").value(addressNumber.format()))
                .andExpect(jsonPath("$.mainAddress.address").value(addressLine))
        ;
    }
}
