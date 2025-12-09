package com.redcare.service;

import com.redcare.calculator.ScoreCalculator;
import com.redcare.client.GithubRestClient;
import com.redcare.client.response.GithubResponse;
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
public class SearchRepositoryServiceTest {

    @Mock
    private GithubRestClient client;
    @Mock
    private ScoreCalculator calculator;

    @InjectMocks
    private SearchRepositoryService service;

    @Test
    void test() {
        GithubResponse.Item githubItem = new GithubResponse.Item("JavaRepository", "url", "Java", 5, 12, OffsetDateTime.now());
        GithubResponse githubResponse = new GithubResponse(12, List.of(githubItem));

        when(client.findRepositories(anyString(), anyString())).thenReturn(githubResponse);
        when(calculator.calculateScore(anyInt(), anyInt(), Mockito.any(OffsetDateTime.class))).thenReturn(10.0);

        Response responses = service.searchRepository("Java", "2021-02-12");
        assertThat(responses.items()).hasSize(1);
        Response.Item item = responses.items().getFirst();
        assertThat(item).isNotNull();
        assertThat(item.name()).isEqualTo("JavaRepository");
        assertThat(item.url()).isEqualTo("url");
        assertThat(item.start()).isEqualTo(5);
        assertThat(item.forks()).isEqualTo(12);
        assertThat(item.score()).isEqualTo(10.0);

        verify(client).findRepositories(Mockito.eq("Java"), Mockito.eq("2021-02-12"));
        verify(calculator).calculateScore(Mockito.eq(5), Mockito.eq(12), Mockito.any(OffsetDateTime.class));
    }
}
