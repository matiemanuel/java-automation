package com.mercadolibre.test;

import com.framework.testsuite.BaseTest;
import com.mercadolibre.models.components.searchresultspage.SearchResultsSideBar;
import com.mercadolibre.models.components.searchresultspage.SearchView;
import com.mercadolibre.models.pages.HomePage;
import com.mercadolibre.models.pages.SearchResultsPage;
import com.mercadolibre.models.pages.SelectCountryPage;
import org.junit.Test;

import static com.mercadolibre.models.components.searchresultspage.SearchView.Options.LOWER_PRICE;
import static com.mercadolibre.models.pages.SelectCountryPage.Country.ARGENTINA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChallengeTest extends BaseTest {

    @Test
    public void mainChallenge() {
        String SEARCH_FOR = "Autos";
        String LOCATION = "Córdoba";

        SelectCountryPage selectCountryPage = new SelectCountryPage();
        HomePage homePage = selectCountryPage.selectCountry(ARGENTINA);

        assertThat(homePage.isVisible(), is(true));

        homePage.getCookieBanner().acceptCookies();
        SearchResultsPage resultsPage = homePage.getNavBar().searchFor(SEARCH_FOR);
        SearchResultsSideBar sideBar = resultsPage.getSideBar();

        assertThat("Current search is not the expected one", sideBar.getCurrentSearch(), is(SEARCH_FOR));

        sideBar.getFilterByIndex(3).clickOption(0);

        assertThat(sideBar.getFilteredValuesQuantityApplied(), is(1));
        assertThat(sideBar.filteredValuesContains("Hasta $ 2.000.000"), is(true));

        sideBar.getFilterByIndex(7).clickOption(LOCATION);

        assertThat(sideBar.getFilteredValuesQuantityApplied(), is(2));
        assertThat(sideBar.filteredValuesContains(LOCATION), is(true));

        int searchResultsQuantity = sideBar.getSearchResultsQuantity();
        SearchView searchView = resultsPage.getSearchView();
        searchView.sortBy(LOWER_PRICE);

        assertThat(searchView.getCurrentSortedValue(), is("Menor precio"));

        System.out.printf(">> [%d] << results for [%s] are being displayed after filtering by: Location: [%s] and cost up to $ 2.000.000 ARS",
                searchResultsQuantity, SEARCH_FOR, LOCATION);
    }
}
