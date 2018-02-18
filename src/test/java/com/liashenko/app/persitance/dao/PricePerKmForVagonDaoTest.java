package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.PricePerKmForVagonDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.PricePerKmForVagonDaoImpl;
import com.liashenko.app.persistance.domain.PricePerKmForVagon;
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
public class PricePerKmForVagonDaoTest extends TestDbUtil {
    private static final Integer NOT_EXISTING_VAGON_TYPE_KEY = Integer.MAX_VALUE;
    private static final Integer EXISTING_VAGON_TYPE_KEY = 2;
    private static final Integer MORE_THAN_ONE_EXISTING_VAGON_TYPE_KEYS = 6;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private PricePerKmForVagonDao testedDao;

    public PricePerKmForVagonDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private PricePerKmForVagon getExpectedEntity() {
        PricePerKmForVagon actualPricePerKmForVagon = new PricePerKmForVagon();
        actualPricePerKmForVagon.setId(2L);
        actualPricePerKmForVagon.setVagonTypeId(EXISTING_VAGON_TYPE_KEY);
        actualPricePerKmForVagon.setPrice(20.77);
        return actualPricePerKmForVagon;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new PricePerKmForVagonDaoImpl(connection, localeBundle);
    }

    @After
    public void closeTestedDaoConnection() {
        close(connection);
    }

    //    Optional<PricePerKmForVagon> getPricePerKmForVagon(Integer vagonTypeId);
    @Test
    public void returnsEmptyOptionalIfIdIsNull() {
        assertFalse(testedDao.getPricePerKmForVagon(null).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfVagonTypeIdIs_0() {
        assertFalse(testedDao.getPricePerKmForVagon(0).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfVagonTypeIdIsLessThan_0() {
        assertFalse(testedDao.getPricePerKmForVagon(-4).isPresent());
    }

    @Test(expected = DAOException.class)
    public void failsIfExistsMoreThanOneVagonTypeIds() {
        testedDao.getPricePerKmForVagon(MORE_THAN_ONE_EXISTING_VAGON_TYPE_KEYS);
    }

    @Test
    public void returnsEmptyOptionalIfVagonTypeIdDoesNotExist() {
        assertFalse(testedDao.getPricePerKmForVagon(NOT_EXISTING_VAGON_TYPE_KEY).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfTableIsEmpty() {
        assertTrue(testedDao.getPricePerKmForVagon(EXISTING_VAGON_TYPE_KEY).isPresent());
    }

    @Test
    public void returnsExpectedResultIfVagonTypeIdExists() {
        PricePerKmForVagon expectedPricePerKmForVagon = getExpectedEntity();
        PricePerKmForVagon actualPricePerKmForVagon = testedDao.getPricePerKmForVagon(EXISTING_VAGON_TYPE_KEY)
                .orElse(null);
        assertNotNull(actualPricePerKmForVagon);
        assertEquals(expectedPricePerKmForVagon.getId(), actualPricePerKmForVagon.getId());
        assertEquals(expectedPricePerKmForVagon.getVagonTypeId(), actualPricePerKmForVagon.getVagonTypeId());
        assertEquals(expectedPricePerKmForVagon.getPrice(), actualPricePerKmForVagon.getPrice());
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        dropTablesInTestDb();
        testedDao.getPricePerKmForVagon(EXISTING_VAGON_TYPE_KEY);
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<User> persist(User object);
    //    Optional<User> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
    //    Optional<List<Entity>> getAll();
}
