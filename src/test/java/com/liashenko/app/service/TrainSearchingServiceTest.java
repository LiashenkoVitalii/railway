package com.liashenko.app.service;


import com.liashenko.app.service.dto.AutocompleteDto;
import com.liashenko.app.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class TrainSearchingServiceTest extends TestDbUtil {
    private static final String PATTERN_DOES_NOT_MATCH_ANY_STATION = "////";
    private static final String UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS = "Київ";
    private static final String EN_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS = "Kyiv";
    private static final int STATIONS_IN_TABLE = 47;

    private static final Long NOT_EXISTING_ROUTE_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ROUTE_ID_KEY = 1L;

    private static final Long EXISTING_FROM_STATION_ID_KEY = 1L;
    private static final Long EXISTING_TO_STATION_ID_KEY = 3L;

    private static final Long MORE_THAN_ONE_STATION_STATION_ID_KEY_BELONGS_TO_ROUTE = 21L;

    private static final Long NOT_EXISTING_FROM_STATION_ID_KEY = 15L;
    private static final Long NOT_EXISTING_TO_STATION_ID_KEY = 15L;
    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private String localePattern;
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private TrainSearchingService testedService;

    public TrainSearchingServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
        this.localePattern = getLocalePattern(localeBundle);
    }

    private String getLocalePattern(ResourceBundle localeBundle) {
        String pattern;
        if (localeBundle.getLocale().getLanguage().equals("uk")) {
            pattern = UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS;
        } else if (localeBundle.getLocale().getLanguage().equals("en")) {
            pattern = EN_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS;
        } else {
            pattern = UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS;
        }
        return pattern;
    }

    private List<AutocompleteDto> createExpectedEntitiesListForPattern(String pattern) {
        List<AutocompleteDto> list = new ArrayList<>();
        switch (pattern) {
            case UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS:
                list.add(new AutocompleteDto(1L, "Київ-Пасажирський"));
                list.add(new AutocompleteDto(26L, "Полтава-Київська"));
                break;
            case EN_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS:
                list.add(new AutocompleteDto(1L, "Kyiv-Pasazhyrs'kyi"));
                list.add(new AutocompleteDto(26L, "Poltava-Kyivs'ka"));
                break;
            default:
                list.add(new AutocompleteDto(1L, "Київ-Пасажирський"));
                list.add(new AutocompleteDto(26L, "Полтава-Київська"));
                break;
        }
        return list;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getTrainSearchingService(localeBundle);
    }

    //    Optional<List<AutocompleteDto>> getStationAutocomplete(String stationLike);
    @Test
    public void returnsExpectedList() {

    }

    @Test(expected = ServiceException.class)
    public void failsIfPatternIsNull() {
        testedService.getStationAutocomplete(null);
    }

    @Test
    public void returnsFullListIfPatternIsEmpty() {
        List<AutocompleteDto> autocompleteList = testedService.getStationAutocomplete("").orElse(null);
        assertNotNull(autocompleteList);
        assertEquals(autocompleteList.size(), STATIONS_IN_TABLE);
    }

    @Test
    public void returnsEmptyListIfPatternDoesNotMatch() {
        List<AutocompleteDto> autocompleteList = testedService.getStationAutocomplete(PATTERN_DOES_NOT_MATCH_ANY_STATION)
                .orElse(null);
        assertNotNull(autocompleteList);
        assertTrue(autocompleteList.isEmpty());
    }

    @Test
    public void returnsExpectedListIfPatternMatches() {
        List<AutocompleteDto> actualAutocompleteList = testedService.getStationAutocomplete(localePattern).orElse(null);
        List<AutocompleteDto> expectedAutocompleteList = createExpectedEntitiesListForPattern(localePattern);
        assertNotNull(actualAutocompleteList);
        assertEquals(expectedAutocompleteList.size(), expectedAutocompleteList.size());
        for (int i = 0; i < expectedAutocompleteList.size(); i++) {
            assertEquals(expectedAutocompleteList.get(i), actualAutocompleteList.get(i));
        }
    }

    //    Optional<List<TrainDto>> getTrainsForTheRouteOnDate(RouteDto routeDto);
    @Test(expected = ServiceException.class)
    public void failsIfRouteDtoIsNull() {
        testedService.getTrainsForTheRouteOnDate(null);
    }
}
