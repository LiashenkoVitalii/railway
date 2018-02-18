package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.RouteDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.RouteDaoImpl;
import com.liashenko.app.persistance.domain.Route;
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
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;


@RunWith(value = Parameterized.class)
public class RouteDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ROUTE_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ROUTE_ID_KEY = 1L;

    private static final Long EXISTING_FROM_STATION_ID_KEY = 1L;
    private static final Long EXISTING_TO_STATION_ID_KEY = 3L;

    private static final Long MORE_THAN_ONE_STATION_STATION_ID_KEY_BELONGS_TO_ROUTE = 21L;

    private static final Long NOT_EXISTING_FROM_STATION_ID_KEY = 15L;
    private static final Long NOT_EXISTING_TO_STATION_ID_KEY = 15L;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private RouteDao testedDao;

    public RouteDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private List<Route> getExpectedRoutesList() {
        List<Route> list = new ArrayList<>();
        list.add(new Route(1L, 1L, 1L, 0F));
        return list;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new RouteDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    Optional<List<Route>> getRoutesByDepartureAndArrivalStationsId(Long departureStationId, Long arrivalStationId);
    @Test
    public void methodReturnsEmptyOptionalIfDepartureStationIdIs_0() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(0L, EXISTING_TO_STATION_ID_KEY)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfDepartureStationIdIsLessThan_0() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(-4L, EXISTING_TO_STATION_ID_KEY)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfDepartureStationIdIsNull() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(null, EXISTING_TO_STATION_ID_KEY)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfArrivalStationIdIs_0() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(EXISTING_FROM_STATION_ID_KEY, 0L)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfArrivalStationIdIsLessThan_0() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(EXISTING_FROM_STATION_ID_KEY, -4L)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfArrivalStationIdIsNull() {
        assertFalse(testedDao.getRoutesByDepartureAndArrivalStationsId(EXISTING_FROM_STATION_ID_KEY, null)
                .isPresent());
    }

    @Test
    public void methodReturnsEmptyListIfNoRouteExistsWithSpecifiedStations1() {
        List<Route> routes = testedDao.getRoutesByDepartureAndArrivalStationsId(NOT_EXISTING_FROM_STATION_ID_KEY,
                EXISTING_TO_STATION_ID_KEY).orElse(null);
        assertNotNull(routes);
        assertEquals(0, routes.size());
    }

    @Test
    public void methodReturnsEmptyListIfNoRouteExistsWithSpecifiedStations2() {
        List<Route> routes = testedDao.getRoutesByDepartureAndArrivalStationsId(2L,
                NOT_EXISTING_TO_STATION_ID_KEY).orElse(null);
        assertNotNull(routes);
        assertEquals(0, routes.size());
    }

    @Test
    public void methodReturnsEmptyListIfNoRouteExistsWithSpecifiedStations3() {
        List<Route> routes = testedDao.getRoutesByDepartureAndArrivalStationsId(NOT_EXISTING_FROM_STATION_ID_KEY,
                NOT_EXISTING_TO_STATION_ID_KEY).orElse(null);
        assertNotNull(routes);
        assertEquals(0, routes.size());
    }

    @Test
    public void methodReturnsExpectedListIfRouteExistsWithSpecifiedStations() {
        List<Route> expectedRoutes = getExpectedRoutesList();
        List<Route> actualRoutes = testedDao.getRoutesByDepartureAndArrivalStationsId(EXISTING_FROM_STATION_ID_KEY,
                EXISTING_TO_STATION_ID_KEY).orElse(null);
        assertNotNull(actualRoutes);
        assertEquals(expectedRoutes.size(), actualRoutes.size());
        for (int i = 0; i < expectedRoutes.size(); i++) {
            assertEquals(expectedRoutes.get(i), actualRoutes.get(i));
        }
    }

    //    Optional<Route> getFirstTerminalStationOnRoute(Long routeId);
    @Test
    public void methodReturnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getFirstTerminalStationOnRoute(0L).isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getFirstTerminalStationOnRoute(-4L).isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getFirstTerminalStationOnRoute(null).isPresent());
    }

    @Test
    public void methodReturnsEmptyOptionalIfRouteIdDoesNotExists() {
        assertFalse(testedDao.getFirstTerminalStationOnRoute(NOT_EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void methodReturnsExpectedResultIfRouteIdExists() {
        Route expectedRoute = getExpectedEntity();
        Route actualRoute = testedDao.getFirstTerminalStationOnRoute(1L).orElse(null);
        assertNotNull(actualRoute);
        assertEquals(expectedRoute.getId(), actualRoute.getId());
        assertEquals(expectedRoute.getStationId(), actualRoute.getStationId());
        assertEquals(expectedRoute.getRouteNumberId(), actualRoute.getRouteNumberId());
        assertEquals(expectedRoute.getDistance(), actualRoute.getDistance());
    }

    @Test(expected = DAOException.class)
    public void methodFailsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getFirstTerminalStationOnRoute(EXISTING_ROUTE_ID_KEY);
    }

    //    Optional<Route> getLastTerminalStationOnRoute(Long routeId);
    @Test
    public void returnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getLastTerminalStationOnRoute(0L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getLastTerminalStationOnRoute(-4L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getLastTerminalStationOnRoute(null).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdDoesNotExists() {
        assertFalse(testedDao.getLastTerminalStationOnRoute(NOT_EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsExpectedResultIfRouteIdExists() {
        Route expectedRoute = new Route(14L, 14L, 1L, 545F);
        Route actualRoute = testedDao.getLastTerminalStationOnRoute(1L).orElse(null);
        assertNotNull(actualRoute);
        assertEquals(expectedRoute.getId(), actualRoute.getId());
        assertEquals(expectedRoute.getStationId(), actualRoute.getStationId());
        assertEquals(expectedRoute.getRouteNumberId(), actualRoute.getRouteNumberId());
        assertEquals(expectedRoute.getDistance(), actualRoute.getDistance());
    }

    @Test
    public void returnsRouteRateIfRouteIdExists() {
        assertTrue(testedDao.getLastTerminalStationOnRoute(EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getLastTerminalStationOnRoute(EXISTING_ROUTE_ID_KEY);
    }

    //    Optional<Route> getStationOnRoute(Long stationId, Long routeId);
    @Test
    public void method_returnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getStationOnRoute(EXISTING_FROM_STATION_ID_KEY, 0L).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getStationOnRoute(EXISTING_FROM_STATION_ID_KEY, -4L).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getStationOnRoute(EXISTING_FROM_STATION_ID_KEY, null).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfStationIdIs_0() {
        assertFalse(testedDao.getStationOnRoute(0L, EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfStationIdIsLessThan_0() {
        assertFalse(testedDao.getStationOnRoute(-4L, EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfStationIdIsNull() {
        assertFalse(testedDao.getStationOnRoute(null, EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void method_returnsEmptyOptionalIfRouteIdDoesNotExists() {
        assertFalse(testedDao.getStationOnRoute(EXISTING_FROM_STATION_ID_KEY, NOT_EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfStationIdDoesNotExists() {
        assertFalse(testedDao.getStationOnRoute(NOT_EXISTING_FROM_STATION_ID_KEY, EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfStationIdDoesNotBelongToRoute() {
        assertFalse(testedDao.getStationOnRoute(20L, EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsExpectedEntityIfStationIdBelongToRoute() {
        Route actualRoute = testedDao.getStationOnRoute(EXISTING_FROM_STATION_ID_KEY, EXISTING_ROUTE_ID_KEY).orElse(null);
        Route expectedRoute = getExpectedEntity();
        assertNotNull(actualRoute);
        assertEquals(expectedRoute.getId(), actualRoute.getId());
        assertEquals(expectedRoute.getStationId(), actualRoute.getStationId());
        assertEquals(expectedRoute.getRouteNumberId(), actualRoute.getRouteNumberId());
        assertEquals(expectedRoute.getDistance(), actualRoute.getDistance());
    }

    @Test(expected = DAOException.class)
    public void failsIfMoreThanOneStationIdBelongToRoute() {
        testedDao.getStationOnRoute(MORE_THAN_ONE_STATION_STATION_ID_KEY_BELONGS_TO_ROUTE, 2L);
    }

    private Route getExpectedEntity() {
        return new Route(1L, 1L, 1L, 0F);
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<User> persist(User object);
    //    Optional<User> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
    //    Optional<List<Entity>> getAll();
}
