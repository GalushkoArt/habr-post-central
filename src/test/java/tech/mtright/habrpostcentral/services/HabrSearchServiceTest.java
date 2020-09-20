package tech.mtright.habrpostcentral.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
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
        assertThat(searchService.searchCompaniesByName("Дойче Банк").get(0)).containsIgnoringCase("Дойче банк");
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

    @Test
    public void searchExactUserByNameTest() {
        assertThat(searchService.searchUsersByName("EvgenyBorisov").get(0).getFullName()).isEqualTo("Борисов Евгений");
        assertThat(searchService.searchUsersByName("EvgenyBorisov").get(0).getName()).isEqualTo("EvgenyBorisov");
    }

    @Test
    public void searchUsersByNameWithMultipleResultsTest() {
        assertThat(searchService.searchUsersByName("Вася")).hasSizeGreaterThan(1);
    }

    @Test
    public void isRelevantTagRelevantTest() {
        assertThat(searchService.isTagRelevant("финансы")).isTrue();
    }

    @Test
    public void isIrrelevantTagIrrelevantTest() {
        assertThat(searchService.isTagRelevant("5d5r37utreyey7")).isFalse();
    }
}