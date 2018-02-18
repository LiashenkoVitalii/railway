package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.VagonTypeDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.VagonTypeDaoImpl;
import com.liashenko.app.persistance.domain.VagonType;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(value = Parameterized.class)
public class VagonTypeDaoTest extends TestDbUtil {

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();

    private ResourceBundle localeBundle;
    private Connection connection;
    private VagonTypeDao testedDao;

    public VagonTypeDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new VagonTypeDaoImpl(connection, localeBundle);
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
        Optional<List<VagonType>> usersOpt = testedDao.getAll();
        assertTrue(usersOpt.isPresent());
        assertEquals(0, usersOpt.get().size());
    }

    @Test(expected = DAOException.class)
    public void failsOnGettingAllFromNotExistingTable() {
        dropTablesInTestDb();
        testedDao.getAll();
    }

    //    boolean isExists(Long key);
    //    void create(Entity object);
    //    Optional<Entity> persist(User object);
    //    Optional<Entity> getByPK(Long key);
    //    void update(Entity object);
    //    void delete(Entity object);
}
