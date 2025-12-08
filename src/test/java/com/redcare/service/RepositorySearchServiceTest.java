package com.redcare.service;

import com.redcare.calculator.ScoreCalculator;
import com.redcare.client.GithubRestClient;
import com.redcare.client.response.GithubItemResponse;
import com.redcare.client.response.GithubResponse;
import com.redcare.rest.respose.ItemResponse;
import com.redcare.rest.respose.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositorySearchServiceTest {

    @Mock
    private GithubRestClient client;
    @Mock
    private ScoreCalculator calculator;

    @InjectMocks
    private RepositorySearchService service;

    @Test
    void test() {
        GithubItemResponse githubItemResponse = new GithubItemResponse("JavaRepository", "url", "Java", 5, 12, OffsetDateTime.now());
        GithubResponse githubResponse = new GithubResponse(12, List.of(githubItemResponse));

        when(client.findRepositories(anyString(), anyString())).thenReturn(githubResponse);
        when(calculator.calculateScore(anyInt(), anyInt(), Mockito.any(OffsetDateTime.class))).thenReturn(10.0);

        Response responses = service.searchRepository("Java", "2021-02-12");
        assertThat(responses.items()).hasSize(1);
        ItemResponse itemResponse = responses.items().getFirst();
        assertThat(itemResponse).isNotNull();
        assertThat(itemResponse.name()).isEqualTo("JavaRepository");
        assertThat(itemResponse.url()).isEqualTo("url");
        assertThat(itemResponse.start()).isEqualTo(5);
        assertThat(itemResponse.forks()).isEqualTo(12);
        assertThat(itemResponse.score()).isEqualTo(10.0);

        verify(client).findRepositories(Mockito.eq("Java"), Mockito.eq("2021-02-12"));
        verify(calculator).calculateScore(Mockito.eq(5), Mockito.eq(12), Mockito.any(OffsetDateTime.class));
    }
}
