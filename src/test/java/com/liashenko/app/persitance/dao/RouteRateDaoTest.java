package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.RouteRateDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.RouteRateDaoImpl;
import com.liashenko.app.persistance.domain.RouteRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.sql.Connection;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class RouteRateDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ROUTE_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ROUTE_ID_KEY = 2L;
    private static final Long MORE_THAN_ONE_EXISTING_ROUTE_ID = 13L;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private RouteRateDao testedDao;

    public RouteRateDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private RouteRate getExpectedEntity() {
        RouteRate actualRouteRate = new RouteRate();
        actualRouteRate.setId(2);
        actualRouteRate.setRate(1F);
        actualRouteRate.setRouteId(EXISTING_ROUTE_ID_KEY);
        return actualRouteRate;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new RouteRateDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    Optional<RouteRate> getByRouteId(Long routeId);
    @Test
    public void returnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getByRouteId(0L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getByRouteId(-4L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getByRouteId(null).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdDoesNotExists() {
        assertFalse(testedDao.getByRouteId(NOT_EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsExpectedResultIfRouteIdExists() {
        RouteRate expectedRouteRate = getExpectedEntity();
        RouteRate actualRouteRate = testedDao.getByRouteId(EXISTING_ROUTE_ID_KEY).orElse(null);
        assertNotNull(actualRouteRate);
        assertEquals(expectedRouteRate.getId(), actualRouteRate.getId());
        assertEquals(expectedRouteRate.getRate(), actualRouteRate.getRate());
        assertEquals(expectedRouteRate.getRouteId(), actualRouteRate.getRouteId());
    }

    @Test
    public void returnsRouteRateIfRouteIdExists() {
        assertTrue(testedDao.getByRouteId(EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getByRouteId(EXISTING_ROUTE_ID_KEY);
    }

    @Test(expected = DAOException.class)
    public void failsIfMoreThanOneRouteIdExists() {
        testedDao.getByRouteId(MORE_THAN_ONE_EXISTING_ROUTE_ID);
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<Entity> persist(Entity object);
    //    Optional<Entity> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
    //    Optional<List<Entity>> getAll();
}
