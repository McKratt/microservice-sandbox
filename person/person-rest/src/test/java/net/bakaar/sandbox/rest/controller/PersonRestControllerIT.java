package net.bakaar.sandbox.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bakaar.sandbox.domain.person.CreatePersonCommand;
import net.bakaar.sandbox.domain.person.Person;
import net.bakaar.sandbox.domain.person.PersonApplicationService;
import net.bakaar.sandbox.rest.PersonRestConfiguration;
import net.bakaar.sandbox.shared.domain.vo.PNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersonRestConfiguration.class)
@WebMvcTest
@AutoConfigureMockMvc
public class PersonRestControllerIT {

    private final String baseUrl = "/rest/api/v1/partners";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private PersonApplicationService service;

    @Test
    public void create_should_return_a_complete_partner() throws Exception {
        String pid = "P34567890";
        String name = "MyName";
        String forename = "MyForename";
        PNumber pNumber = PNumber.of(pid);
        Person returnedPerson = Person.of(name, forename, null).withId(pNumber).build();
        given(service.createPartner(any(CreatePersonCommand.class))).willReturn(returnedPerson);
        mockMvc
                .perform(post(baseUrl)
                        .accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{" +
                                "  \"name\": \"myName\"," +
                                "  \"forename\": \"myForename\"," +
                                "  \"birthDate\": \"12.03.1945\"" +
                                "}")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("P34567890"))
        ;


    }

    @Test
    public void readAPartner_should_answer_OK() throws Exception {
        long id = 56743245L;
        PNumber pNumber = PNumber.of(id);
        String name = "MyName";
        String forename = "MyForename";
        Person returnedDto = Person.of(name, forename, null).withId(pNumber).build();
        given(service.readPartner(any())).willReturn(returnedDto);
        mockMvc.perform(get(baseUrl + "/" + pNumber.format())
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.forename").value(forename))
                .andExpect(jsonPath("$.id").value(pNumber.format()));
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
