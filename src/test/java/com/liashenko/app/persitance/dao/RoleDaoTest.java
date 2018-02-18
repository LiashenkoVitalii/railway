package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.RoleDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.RoleDaoImpl;
import com.liashenko.app.persistance.domain.Role;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class RoleDaoTest extends TestDbUtil {
    private static final Long NOT_EXISTING_ENTITY_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ENTITY_KEY = 2L;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private RoleDao testedDao;

    public RoleDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    private Role getExpectedEntity() {
        Role actualRole = createEntity();
        actualRole.setId(EXISTING_ENTITY_KEY);
        return actualRole;
    }

    private Role createEntity() {
        Role actualRole = new Role();
        if (localeBundle.getLocale().getLanguage().equals("uk")) {
            actualRole.setName("Користувач");
        } else if (localeBundle.getLocale().getLanguage().equals("en")) {
            actualRole.setName("User");
        } else {
            actualRole.setName("Користувач");
        }
        return actualRole;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new RoleDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    //    boolean isExists(Long key);
    @Test
    public void failsIfKeyLessThan_0() {
        assertFalse(testedDao.isExists(-1L));
    }

    @Test
    public void returnsFalseIfKeyIs_0() {
        assertFalse(testedDao.isExists(0L));
    }

    @Test
    public void failsIfKeyIs_Null() {
        assertFalse(testedDao.isExists(null));
    }

    @Test
    public void returnsFalseIfKeyDoesNotExist() {
        assertFalse(testedDao.isExists(NOT_EXISTING_ENTITY_KEY));
    }

    @Test
    public void returnsTrueIfKeyExists() {
        assertTrue(testedDao.isExists(EXISTING_ENTITY_KEY));
    }

    @Test
    public void returnsFalseIfTableIs_Empty() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        assertFalse(testedDao.isExists(EXISTING_ENTITY_KEY));
    }

    //    void create(Entity object);
    @Test
    public void isAddedOneNewRowInTable() {
        List<Role> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        testedDao.create(createEntity());
        List<Role> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(1, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIsNull() {
        testedDao.create(null);
    }

    //    Optional<User> persist(User object);
    @Test//encoding
    public void isPersistedEntityEqualsToOriginal() {
        Role originalEntity = createEntity();
        Role persistedEntity = testedDao.persist(originalEntity).orElse(null);
        assertNotNull(persistedEntity);
        assertEquals(originalEntity.getName(), persistedEntity.getName());
    }

    @Test(expected = DAOException.class)
    public void failsIfPersistedEntityIsNull() {
        testedDao.persist(null);
    }

    //    Optional<User> getByPK(Long key);
    @Test
    public void returnsEmptyOptionalIfKeyNotExists() {
        assertFalse(testedDao.getByPK(NOT_EXISTING_ENTITY_KEY).isPresent());
    }

    @Test
    public void isReturnedEntityEqualsToActual() {
        Role expectedEntity = getExpectedEntity();
        Role actualEntity = testedDao.getByPK(2L).orElse(null);
        assertNotNull(actualEntity);
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
    }

    @Test
    public void failsIfPKeyLessThan_0() {
        assertFalse(testedDao.getByPK(-3L).isPresent());
    }

    @Test
    public void failsIfPKeyIs_0() {
        assertFalse(testedDao.getByPK(0L).isPresent());
    }

    @Test
    public void failsIfPKeyIsNull() {
        assertFalse(testedDao.getByPK(null).isPresent());
    }

    //    void update(Entity object);
    @Test(expected = DAOException.class)
    public void failsIfEntityToUpdateIsNull() {
        testedDao.update(null);
    }

    @Test
    public void returnsFalseIfRowsCountInDbChanged() {
        List<Role> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        List<Role> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(0, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIsNull() {
        Role role = createEntity();
        testedDao.update(role);
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIs_0() {
        Role role = createEntity();
        role.setId(0L);
        testedDao.update(role);
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIsNotExists() {
        Role role = createEntity();
        role.setId(NOT_EXISTING_ENTITY_KEY);
        testedDao.update(role);
    }

    //    Optional<List<Entity>> getAll();
    @Test
    public void returnsEmptyListOnGettingAllFromEmptyTable() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        Optional<List<Role>> usersOpt = testedDao.getAll();
        assertTrue(usersOpt.isPresent());
        assertEquals(0, usersOpt.get().size());
    }

    @Test(expected = DAOException.class)
    public void FailsOnGettingAllFromNotExistingTable() {
        dropTablesInTestDb();
        testedDao.getAll();
    }

    //    void delete(Entity object);
    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityIsNull() {
        Role roleIsNull = null;
        testedDao.delete(roleIsNull);
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        Role existedRole = getExpectedEntity();
        dropTablesInTestDb();
        testedDao.delete(existedRole);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityDoesNotExistInDb() {
        Role notExistedRole = createEntity();
        notExistedRole.setId(NOT_EXISTING_ENTITY_KEY);
        testedDao.delete(notExistedRole);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasId_0() {
        Role notExistedRole = createEntity();
        notExistedRole.setId(0L);
        testedDao.delete(notExistedRole);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasIdWhichLessThan_0() {
        Role notExistedRole = createEntity();
        notExistedRole.setId(-3L);
        testedDao.delete(notExistedRole);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasIdWith_null_Value() {
        Role notExistedRole = createEntity();
        notExistedRole.setId(null);
        testedDao.delete(notExistedRole);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntitysIdExists() {
        Role notExistedRole = createEntity();
        notExistedRole.setId(2L);
        testedDao.delete(notExistedRole);
    }
}
