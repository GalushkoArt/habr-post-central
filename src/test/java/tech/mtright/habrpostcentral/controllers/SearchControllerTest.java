package tech.mtright.habrpostcentral.controllers;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.mtright.habrpostcentral.dto.HabrUser;
import tech.mtright.habrpostcentral.services.SiteSearchService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ControllerMockConfiguration.class)
public class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteSearchService searchService;

    @Before
    public void setUp() {
        Mockito.when(searchService.searchCompaniesByName(anyString())).thenReturn(List.of("Test Bank", "Тестовый Банк"));
        Mockito.when(searchService.searchHubsByName(anyString())).thenReturn(List.of("Test hub", "Тестовый хаб"));
        Mockito.when(searchService.searchUsersByName(anyString()))
                .thenReturn(List.of(HabrUser.builder().fullName("Vasiliy").nickName("Vasechka").build(),
                                    HabrUser.builder().fullName("Пётр").nickName("Петрович").build()));
        Mockito.when(searchService.isTagRelevant(anyString())).thenReturn(true);
    }

    @SneakyThrows
    @Test
    public void getCompaniesByNameTest() {
        MvcResult result = makeGetRequest("/api/getCompanies?name=Тестовый%банк");
        assertThat(result.getResponse().getContentAsString()).contains("Test Bank").contains("Тестовый Банк");
    }

    @SneakyThrows
    @Test
    public void getHubsByNameTest() {
        MvcResult result = makeGetRequest("/api/getHubs?name=Тестовый%хаб");
        assertThat(result.getResponse().getContentAsString()).contains("Test hub").contains("Тестовый хаб");
    }

    @SneakyThrows
    @Test
    public void isTagRelevantTest() {
        MvcResult result = makeGetRequest("/api/isTagRelevant?tag=Тестовый%тег");
        assertThat(result.getResponse().getContentAsString()).contains("true");
    }

    @SneakyThrows
    @Test
    public void findAuthorsByNameTest() {
        MvcResult result = makeGetRequest("/api/findAuthors?name=Тестовое%имя");
        assertThat(result.getResponse().getContentAsString())
                .contains("Vasiliy").contains("Vasechka")
                .contains("Пётр").contains("Петрович");
    }

    private MvcResult makeGetRequest(String getRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(getRequest)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
    }
}