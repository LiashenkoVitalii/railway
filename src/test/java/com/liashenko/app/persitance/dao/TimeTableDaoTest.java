package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.TimeTableDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.TimeTableDaoImpl;
import com.liashenko.app.persistance.domain.TimeTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(value = Parameterized.class)
public class TimeTableDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ROUTE_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ROUTE_ID_KEY = 1L;
    private static final Long NOT_EXISTING_DEPARTURE_STATION_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_DEPARTURE_STATION_KEY = 5L;
    private static final LocalDate EXISTING_DATE = LocalDate.parse("08.12.2017", DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();

    private ResourceBundle localeBundle;
    private Connection connection;
    private TimeTableDao testedDao;

    public TimeTableDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private TimeTable getExpectedEntity() {
        TimeTable actualTimeTable = new TimeTable();
        actualTimeTable.setRouteNumberId(EXISTING_ROUTE_ID_KEY);
        actualTimeTable.setDeparture(LocalDateTime.of(EXISTING_DATE,
                LocalTime.parse("03.14.00", DateTimeFormatter.ofPattern("HH.mm.ss"))));

        actualTimeTable.setArrival(LocalDateTime.of(EXISTING_DATE,
                LocalTime.parse("03.12.00", DateTimeFormatter.ofPattern("HH.mm.ss"))));

        actualTimeTable.setId(32L);
        actualTimeTable.setStationId(EXISTING_DEPARTURE_STATION_KEY);
        return actualTimeTable;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new TimeTableDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    Optional<TimeTable> getTimeTableForStationByDataAndRoute(Long departureStationId, Long routeId, LocalDate date);
    @Test
    public void returnsEmptyOptionalIfDepartureStationIdIsNull() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(null, EXISTING_ROUTE_ID_KEY, EXISTING_DATE)
                .isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfDepartureStationIdIs_0() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(0L, EXISTING_ROUTE_ID_KEY, EXISTING_DATE)
                .isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfDepartureStationIdIsLessThan_0() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(-2L, EXISTING_ROUTE_ID_KEY, EXISTING_DATE)
                .isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfDepartureStationIdDoesNotExist() {
        testedDao.getTimeTableForStationByDataAndRoute(NOT_EXISTING_DEPARTURE_STATION_ID_KEY, EXISTING_ROUTE_ID_KEY, EXISTING_DATE);
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY, EXISTING_ROUTE_ID_KEY, EXISTING_DATE);
    }

    @Test(expected = DAOException.class)
    public void failsIfMoreThanOneResultExist() {
        testedDao.getTimeTableForStationByDataAndRoute(22L, 12L,
                LocalDate.parse("09.12.2017", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY, null, EXISTING_DATE)
                .isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY, 0L, EXISTING_DATE)
                .isPresent());

    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY, -5L, EXISTING_DATE)
                .isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdDoesNotExist() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY,
                NOT_EXISTING_ROUTE_ID_KEY, EXISTING_DATE).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfDateIsNull() {
        assertFalse(testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY, NOT_EXISTING_ROUTE_ID_KEY, null)
                .isPresent());
    }

    @Test
    public void returnsExpectedTimeTable() {
        TimeTable expectedTimeTable = getExpectedEntity();
        TimeTable actualTimeTable = testedDao.getTimeTableForStationByDataAndRoute(EXISTING_DEPARTURE_STATION_KEY,
                EXISTING_ROUTE_ID_KEY, EXISTING_DATE).orElse(null);
        assertNotNull(actualTimeTable);
        assertEquals(expectedTimeTable.getId(), actualTimeTable.getId());
        assertEquals(expectedTimeTable.getArrival(), actualTimeTable.getArrival());
        assertEquals(expectedTimeTable.getDeparture(), actualTimeTable.getDeparture());
        assertEquals(expectedTimeTable.getRouteNumberId(), actualTimeTable.getRouteNumberId());
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<Entity> persist(Entity object);
    //    Optional<Entity> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
    //    Optional<List<Entity>> getAll();
}
