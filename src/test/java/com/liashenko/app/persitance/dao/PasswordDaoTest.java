package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.PasswordDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.PasswordDaoImpl;
import com.liashenko.app.persistance.domain.Password;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static junit.framework.TestCase.assertEquals;

@RunWith(value = Parameterized.class)
public class PasswordDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ENTITY_KEY = Long.MAX_VALUE;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private PasswordDao testedDao;

    public PasswordDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private Password createEntity() {
        Password actualPassword = new Password();
        actualPassword.setIterations(1000);
        actualPassword.setAlgorithm("PBKDF2WithHmacSHA1");
        actualPassword.setPassword(new byte[]{0, 0, 0, 0, 0});
        actualPassword.setSalt(new byte[]{0, 0, 0, 0, 0});
        return actualPassword;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new PasswordDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    void create(Entity object);
    @Test
    public void isAddedOneNewRowInTable() {
        List<Password> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        testedDao.create(createEntity());
        List<Password> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(1, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIsNull() {
        testedDao.create(null);
    }

    //    void update(Entity object);
    @Test(expected = DAOException.class)
    public void failsIfEntityToUpdateIsNull() {
        testedDao.update(null);
    }

    @Test
    public void returnsFalseIfRowsCountInDbChanged() {
        List<Password> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        List<Password> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(0, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIsNull() {
        Password password = createEntity();
        testedDao.update(password);
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIs_0() {
        Password password = createEntity();
        password.setId(0L);
        testedDao.update(password);
    }

    @Test(expected = DAOException.class)
    public void returnsFalseIfEntityIdIsNotExists() {
        Password password = createEntity();
        password.setId(NOT_EXISTING_ENTITY_KEY);
        testedDao.update(password);
    }

    //    boolean isExists(Long key);
    //    Optional<User> persist(User object);
    //    Optional<User> getByPK(Long key);
    //    void delete(Entity object);
    //    Optional<List<Entity>> getAll();
}
