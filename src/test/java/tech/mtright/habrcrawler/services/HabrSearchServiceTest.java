package tech.mtright.habrcrawler.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceMockConfiguration.class)
public class HabrSearchServiceTest {
    @Autowired
    HabrSearchService searchService;

    @Test
    public void findLastPostOnSiteTest() {
        assertThat(searchService.findLastPostOnSite()).isGreaterThan(500000);
    }

    @Test
    public void searchExactCompanyByNameTest() {
        assertThat(searchService.searchCompaniesByName("Райффайзенбанк").get(0)).containsIgnoringCase("Райффайзенбанк");
    }

    @Test
    public void searchCompaniesByNameWithMultipleResultsTest() {
        assertThat(searchService.searchCompaniesByName("банк")).hasSizeGreaterThan(1);
    }
    @Test
    public void searchExactHubByNameTest() {
        assertThat(searchService.searchHubsByName("Транспорт").get(0)).containsIgnoringCase("Транспорт");
    }

    @Test
    public void searchHubsByNameWithMultipleResultsTest() {
        assertThat(searchService.searchCompaniesByName("наука")).hasSizeGreaterThan(1);
    }
}