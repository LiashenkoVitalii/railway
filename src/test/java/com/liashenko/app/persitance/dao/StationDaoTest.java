package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.StationDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.StationDaoImpl;
import com.liashenko.app.persistance.domain.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class StationDaoTest extends TestDbUtil {
    private static final String PATTERN_DOES_NOT_MATCH_ANY_STATION = "////";
    private static final String UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS = "Київ";
    private static final String EN_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS = "Kyiv";
    private static final int STATIONS_IN_TABLE = 47;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private StationDao testedDao;
    private String localePattern;

    public StationDaoTest(ResourceBundle localeBundle) {
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

    private List<Station> createExpectedEntitiesListForPattern(String pattern) {
        List<Station> list = new ArrayList<>();
        switch (pattern) {
            case UA_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS:
                list.add(new Station(1L, "Київ", "Київ-Пасажирський"));
                list.add(new Station(26L, "Полтава", "Полтава-Київська"));
                break;
            case EN_PATTERN_FOR_STATION_EXPECTED_LIST_OF_STATIONS:
                list.add(new Station(1L, "Kyiv", "Kyiv-Pasazhyrs'kyi"));
                list.add(new Station(26L, "Poltava", "Poltava-Kyivs'ka"));
                break;
            default:
                list.add(new Station(1L, "Київ", "Київ-Пасажирський"));
                list.add(new Station(26L, "Полтава", "Полтава-Київська"));
                break;
        }
        return list;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new StationDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    Optional<List<Entity>> getAll();
    @Test
    public void returnsEmptyListOnGettingAllFromEmptyTable() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        Optional<List<Station>> usersOpt = testedDao.getAll();
        assertTrue(usersOpt.isPresent());
        assertEquals(0, usersOpt.get().size());
    }

    @Test(expected = DAOException.class)
    public void FailsOnGettingAllFromNotExistingTable() {
        dropTablesInTestDb();
        testedDao.getAll();
    }

    //    Optional<List<Station>> getStationsLike(String likePattern);
    @Test(expected = DAOException.class)
    public void failsIfPatternIsNull() {
        testedDao.getStationsLike(null);
    }

    @Test
    public void returnsFullListIfPatternIsEmpty() {
        List<Station> stations = testedDao.getStationsLike("").orElse(null);
        assertNotNull(stations);
        assertEquals(stations.size(), STATIONS_IN_TABLE);
    }

    @Test
    public void returnsEmptyListIfPatternDoesNotMatch() {
        List<Station> stations = testedDao.getStationsLike(PATTERN_DOES_NOT_MATCH_ANY_STATION).orElse(null);
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }

    @Test(expected = DAOException.class)
    public void failsIfTableIsEmpty() {
        dropTablesInTestDb();
        testedDao.getStationsLike(localePattern);
    }

    @Test
    public void returnsExpectedListIfPatternMatches() {
        List<Station> actualStations = testedDao.getStationsLike(localePattern).orElse(null);
        List<Station> expectedStations = createExpectedEntitiesListForPattern(localePattern);
        assertNotNull(actualStations);
        assertEquals(expectedStations.size(), actualStations.size());
        for (int i = 0; i < expectedStations.size(); i++) {
            assertEquals(expectedStations.get(i), actualStations.get(i));
        }
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<User> persist(User object);
    //    Optional<User> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
}
