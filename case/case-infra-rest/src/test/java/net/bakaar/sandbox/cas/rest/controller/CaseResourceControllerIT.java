package net.bakaar.sandbox.cas.rest.controller;

import net.bakaar.sandbox.cas.domain.CreateCaseUseCase;
import net.bakaar.sandbox.cas.domain.entity.Case;
import net.bakaar.sandbox.cas.rest.CaseRestConfiguration;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CaseRestConfiguration.class)
@WebMvcTest
@AutoConfigureMockMvc
public class CaseResourceControllerIT {

    private final String baseUrl = "/rest/api/v1/cases";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreateCaseUseCase service;

    @Test
    public void createCase_should_return_case() throws Exception {
        //Given
        String pNumber = "P98765432";
        String id = "MyId";
        Case returnedCase = Case.builder().withBusinnessId(id).withInjured(PNumber.of(pNumber));
        given(service.createCase(any())).willReturn(returnedCase);
        //When //Then
        mockMvc.perform(post(baseUrl)
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content(String.format("{\"insuredNumber\": \"%s\"}", pNumber)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.injured.pnummer").value(pNumber))
                .andExpect(jsonPath("$.id").value(id));

    }
}
