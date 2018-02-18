package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.TrainDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.TrainDaoImpl;
import com.liashenko.app.persistance.domain.Train;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class TrainDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ROUTE_ID_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ROUTE_ID_KEY = 2L;
    private static final Long MORE_THAN_ONE_EXISTING_ROUTE_ID = 13L;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private TrainDao testedDao;

    public TrainDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private Train getExpectedEntity() {
        Train actualTrain = new Train();
        actualTrain.setId(2L);
        actualTrain.setVagonCount(20);
        actualTrain.setNumber("191К");
        actualTrain.setRouteNumId(EXISTING_ROUTE_ID_KEY);
        return actualTrain;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new TrainDaoImpl(connection, localeBundle);
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
        Optional<List<Train>> usersOpt = testedDao.getAll();
        assertTrue(usersOpt.isPresent());
        assertEquals(0, usersOpt.get().size());
    }

    @Test(expected = DAOException.class)
    public void FailsOnGettingAllFromNotExistingTable() {
        dropTablesInTestDb();
        testedDao.getAll();
    }

    //    Optional<Train> getByRoute(Long routeId);
    @Test
    public void returnsEmptyOptionalIfRouteIdIs_0() {
        assertFalse(testedDao.getByRoute(0L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsLessThan_0() {
        assertFalse(testedDao.getByRoute(-4L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdIsNull() {
        assertFalse(testedDao.getByRoute(null).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfRouteIdDoesNotExists() {
        assertFalse(testedDao.getByRoute(NOT_EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test
    public void returnsExpectedResultIfRouteIdExists() {
        Train expectedTrain = getExpectedEntity();
        Train actualTrain = testedDao.getByRoute(EXISTING_ROUTE_ID_KEY).orElse(null);
        assertNotNull(actualTrain);
        assertEquals(expectedTrain.getId(), actualTrain.getId());
        assertEquals(expectedTrain.getNumber(), actualTrain.getNumber());
        assertEquals(expectedTrain.getVagonCount(), actualTrain.getVagonCount());
        assertEquals(expectedTrain.getRouteNumId(), actualTrain.getRouteNumId());
    }

    @Test
    public void returnsTrainIfRouteIdExists() {
        assertTrue(testedDao.getByRoute(EXISTING_ROUTE_ID_KEY).isPresent());
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getByRoute(EXISTING_ROUTE_ID_KEY);
    }

    @Test(expected = DAOException.class)
    public void failsIfMoreThanOneRouteIdExists() {
        testedDao.getByRoute(MORE_THAN_ONE_EXISTING_ROUTE_ID);
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<User> persist(User object);
    //    Optional<User> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
}
