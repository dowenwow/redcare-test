package com.redcare.rest;

import com.redcare.exception.InvalidCreatedAfterException;
import com.redcare.rest.respose.BedRequestResponse;
import com.redcare.rest.respose.Response;
import com.redcare.service.SearchRepositoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RepositorySearchRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SearchRepositoryService service;

    @Test
    void shouldInvokeSearchServiceWithNullParameters() throws Exception {
        Response.Item item = new Response.Item("RepositoryName", "url", "Java", 4, 12, 14.5);
        Response response = new Response(7, List.of(item));

        when(service.searchRepository(Mockito.isNull(), Mockito.isNull())).thenReturn(response);

        mockMvc.perform(get("/search/repositories")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service).searchRepository(Mockito.isNull(), Mockito.isNull());
    }

    @Test
    void shouldInvokeSearchServiceWithNullCreatedAfter() throws Exception {
        Response.Item item = new Response.Item("RepositoryName", "url", "Java", 4, 12, 14.5);
        Response response = new Response(7, List.of(item));

        when(service.searchRepository(Mockito.anyString(), Mockito.isNull())).thenReturn(response);

        mockMvc.perform(get("/search/repositories?language=Java")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service).searchRepository(Mockito.eq("Java"), Mockito.isNull());
    }

    @Test
    void shouldInvokeSearchServiceWithNullLanguage() throws Exception {
        Response.Item item = new Response.Item("RepositoryName", "url", "Java", 4, 12, 14.5
        );
        Response response = new Response(7, List.of(item));

        when(service.searchRepository(Mockito.isNull(), Mockito.anyString()))
                .thenReturn(response);

        mockMvc.perform(get("/search/repositories?createdAfter=2021-02-12")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service).searchRepository(Mockito.isNull(), Mockito.eq("2021-02-12"));
    }

    @Test
    void shouldRespondWithBadRequest()  throws Exception {
        when(service.searchRepository(Mockito.isNull(), Mockito.anyString()))
                .thenThrow(new InvalidCreatedAfterException("createdAfter must match format yyyy-MM-dd"));

        mockMvc.perform(get("/search/repositories?createdAfter=201-0-12")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(new BedRequestResponse("createdAfter must match format yyyy-MM-dd"))));
    }
}
