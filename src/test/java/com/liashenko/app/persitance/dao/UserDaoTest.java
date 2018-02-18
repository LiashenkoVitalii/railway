package com.liashenko.app.persitance.dao;

import com.liashenko.app.persistance.dao.UserDao;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.dao.mysql.UserDaoImpl;
import com.liashenko.app.persistance.domain.User;
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
public class UserDaoTest extends TestDbUtil {

    private static final Long NOT_EXISTING_ENTITY_KEY = Long.MAX_VALUE;
    private static final Long EXISTING_ENTITY_KEY = 2L;
    private static final String SINGLE_EXISTING_EMAIL = "user@mail.com";
    private static final String NO_EXISTING_EMAIL = "user123@123.com";
    private static final String MORE_THAN_ONE_EXISTING_EMAIL = "email@mm.ua";
    private static final Long NO_EXISTING_USER_ID = 1000L;
    private static final int ROWS_IN_TABLE = 25;
    private static final Integer DEFAULT_USERS_COUNT_ON_PAGE = 5;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ResourceBundle localeBundle;
    private Connection connection;
    private UserDao testedDao;

    public UserDaoTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void createDao() {
        connection = getConnection();
        testedDao = new UserDaoImpl(connection, localeBundle);
    }

    @After
    public void dropTestDbAndFlush() {
        close(connection);
    }

    private User getExpectedEntity() {
        User actualUser = new User();
        actualUser.setId(25L);
        actualUser.setEmail(SINGLE_EXISTING_EMAIL);
        actualUser.setFirstName("user");
        actualUser.setLastName("user");
        actualUser.setRoleId(2L);
        actualUser.setPasswordId(21L);
        actualUser.setBanned(Boolean.FALSE);
        actualUser.setLanguage("uk_UA");
        return actualUser;
    }

    private User createEntity() {
        User actualUser = new User();
        actualUser.setEmail(SINGLE_EXISTING_EMAIL);
        actualUser.setFirstName("user");
        actualUser.setLastName("user");
        actualUser.setRoleId(2L);
        actualUser.setPasswordId(21L);
        actualUser.setBanned(Boolean.FALSE);
        actualUser.setLanguage("uk_UA");
        return actualUser;
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
        List<User> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        testedDao.create(createEntity());
        List<User> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(1, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIsNull() {
        testedDao.create(null);
    }

    //    Optional<User> persist(User object);
    @Test
    public void isPersistedEntityEqualsToOriginal() {
        User originalEntity = createEntity();
        User persistedEntity = testedDao.persist(originalEntity).orElse(null);
        assertNotNull(persistedEntity);
        assertEquals(originalEntity.getFirstName(), persistedEntity.getFirstName());
        assertEquals(originalEntity.getLastName(), persistedEntity.getLastName());
        assertEquals(originalEntity.getEmail(), persistedEntity.getEmail());
        assertEquals(originalEntity.getRoleId(), persistedEntity.getRoleId());
        assertEquals(originalEntity.getPasswordId(), persistedEntity.getPasswordId());
        assertEquals(originalEntity.getBanned(), persistedEntity.getBanned());
        assertEquals(originalEntity.getLanguage(), persistedEntity.getLanguage());
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
        User expectedEntity = getExpectedEntity();
        User actualEntity = testedDao.getByPK(25L).orElse(null);
        assertNotNull(actualEntity);
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getFirstName(), actualEntity.getFirstName());
        assertEquals(expectedEntity.getLastName(), actualEntity.getLastName());
        assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        assertEquals(expectedEntity.getRoleId(), actualEntity.getRoleId());
        assertEquals(expectedEntity.getPasswordId(), actualEntity.getPasswordId());
        assertEquals(expectedEntity.getBanned(), actualEntity.getBanned());
        assertEquals(expectedEntity.getLanguage(), actualEntity.getLanguage());
    }

    @Test
    public void returnsEmptyOptionalIfPKeyLessThan_0() {
        assertFalse(testedDao.getByPK(-3L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfPKeyIs_0() {
        assertFalse(testedDao.getByPK(0L).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfPKeyIsNull() {
        assertFalse(testedDao.getByPK(null).isPresent());
    }

    //    void update(Entity object);
    @Test(expected = DAOException.class)
    public void failsIfEntityToUpdateIsNull() {
        testedDao.update(null);
    }

    @Test
    public void returnsFalseIfRowsCountInDbChanged() {
        List<User> beforeEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        List<User> afterEntitiesList = testedDao.getAll().orElse(Collections.emptyList());
        assertEquals(0, afterEntitiesList.size() - beforeEntitiesList.size());
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIsNull() {
        User user = createEntity();
        testedDao.update(user);
    }

    @Test(expected = DAOException.class)
    public void failsIfEntityIdIs_0() {
        User user = createEntity();
        user.setId(0L);
        testedDao.update(user);
    }

    @Test(expected = DAOException.class)
    public void returnsFalseIfEntityIdIsNotExists() {
        User user = createEntity();
        user.setId(NO_EXISTING_USER_ID);
        testedDao.update(user);
    }

//    void delete(Entity object);


    //    Optional<List<Entity>> getAll();
    @Test
    public void returnsEmptyListOnGettingAllFromEmptyTable() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        Optional<List<User>> usersOpt = testedDao.getAll();
        assertTrue(usersOpt.isPresent());
        assertEquals(0, usersOpt.get().size());
    }

    @Test(expected = DAOException.class)
    public void FailsOnGettingAllFromNotExistingTable() {
        dropTablesInTestDb();
        testedDao.getAll();
    }

    //    isEmailExists
    @Test
    public void returnsFalseIfEmailIsEmpty() {
        assertFalse(testedDao.isEmailExists(""));
    }

    @Test
    public void returnsFalseIfEmailIsNull() {
        assertFalse(testedDao.isEmailExists(null));
    }

    @Test
    public void returnsFalseIfEmailIsNotExist() {
        assertFalse(testedDao.isEmailExists(NO_EXISTING_EMAIL));
    }

    @Test
    public void returnsTrueIfEmailExistsInTable() {
        assertTrue(testedDao.isEmailExists(SINGLE_EXISTING_EMAIL));
    }

    //    getUserByEmail(String email);
    @Test
    public void returnsEmptyOptionalIfUserWithEmailNotExists() {
        assertFalse(testedDao.getUserByEmail(NO_EXISTING_EMAIL).isPresent());
    }

    @Test(expected = DAOException.class)
    public void failsIfMoreThanOneUserExistsWithEmail() {
        testedDao.getUserByEmail(MORE_THAN_ONE_EXISTING_EMAIL);
    }

    @Test
    public void failsIfEmailIsNull() {
        assertFalse(testedDao.getUserByEmail(null).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfUserTableIsEmpty() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        assertFalse(testedDao.getUserByEmail(SINGLE_EXISTING_EMAIL).isPresent());
    }

    @Test
    public void returnsOptionalWithUserByEmail() {
        User actualEntity = testedDao.getUserByEmail(SINGLE_EXISTING_EMAIL).orElse(null);
        User expectedEntity = getExpectedEntity();
        assertNotNull(actualEntity);
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getFirstName(), actualEntity.getFirstName());
        assertEquals(expectedEntity.getLastName(), actualEntity.getLastName());
        assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        assertEquals(expectedEntity.getPasswordId(), actualEntity.getPasswordId());
        assertEquals(expectedEntity.getRoleId(), actualEntity.getRoleId());
        assertEquals(expectedEntity.getLanguage(), actualEntity.getLanguage());
    }

    //    boolean isOtherUsersWithEmailExist(Long id, String email);
    @Test
    public void returnsTrueIfNotOnlyCurrentUserExistWithEmail() {
        assertTrue(testedDao.isOtherUsersWithEmailExist(6L, MORE_THAN_ONE_EXISTING_EMAIL));
    }

    @Test
    public void returnsFalseIfNoMoreUsersExistWithEmail() {
        assertFalse(testedDao.isOtherUsersWithEmailExist(25L, SINGLE_EXISTING_EMAIL));
    }

    @Test
    public void returnsFalseIfTableIsEmpty() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        assertFalse(testedDao.isOtherUsersWithEmailExist(6L, SINGLE_EXISTING_EMAIL));
    }

    @Test(expected = DAOException.class)
    public void failsIfUserIdIsLessThan_0() {
        testedDao.isOtherUsersWithEmailExist(-6L, SINGLE_EXISTING_EMAIL);
    }

    @Test(expected = DAOException.class)
    public void failsIfUserIdIs_0() {
        assertFalse(testedDao.isOtherUsersWithEmailExist(0L, SINGLE_EXISTING_EMAIL));
    }

    @Test
    public void returnsTrueIfUserIdIsNotExist() {
        assertTrue(testedDao.isOtherUsersWithEmailExist(NO_EXISTING_USER_ID, SINGLE_EXISTING_EMAIL));
    }

    @Test
    public void returnsFalseIfUserEmailIsEmpty() {
        assertFalse(testedDao.isOtherUsersWithEmailExist(6L, ""));
    }

    @Test
    public void returnsFalseIfUsersEmailIsNull() {
        assertFalse(testedDao.isOtherUsersWithEmailExist(6L, null));
    }

    @Test(expected = DAOException.class)
    public void returnsFalseIfUsersIdIsNull() {
        testedDao.isOtherUsersWithEmailExist(null, SINGLE_EXISTING_EMAIL);
    }

    //    Integer getCount();
    @Test
    public void returns_0_if_tableIsEmpty() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        assertEquals((int) testedDao.getCount(), 0);
    }

    @Test
    public void returns_25_FromTable() {
        assertEquals((int) testedDao.getCount(), ROWS_IN_TABLE);
    }

    //    Optional<List<User>> getPages(int rowsPerPage, int offset);
    @Test
    public void failsIf_rowsPerPage_Is_0() {
        Optional<List<User>> usersOpt = testedDao.getPages(0, 5);
        int actualUsersCount = 0;
        if (usersOpt.isPresent()) {
            actualUsersCount = usersOpt.get().size();
        }
        assertEquals(ROWS_IN_TABLE, actualUsersCount);
    }

    @Test(expected = DAOException.class)
    public void failsIf_rowsPerPage_IsLessThan_0() {
        testedDao.getPages(-3, 5);
    }

    @Test(expected = DAOException.class)
    public void failsIf_offset_IsLessThan_0() {
        testedDao.getPages(DEFAULT_USERS_COUNT_ON_PAGE, -1);
    }

    @Test
    public void returnsPagesWithUsers() {
        assertTrue(testedDao.getPages(DEFAULT_USERS_COUNT_ON_PAGE, 5).isPresent());
    }

    @Test
    public void returnsEmptyOptionalIfTableIsEmpty() {
        dropTablesInTestDb();
        prepareEmptyTablesInTestDb();
        List<User> userList = testedDao.getPages(DEFAULT_USERS_COUNT_ON_PAGE, 5).orElse(null);
        assertNotNull(userList);
        assertEquals(0, userList.size());
    }

    //    void delete(Entity object);
    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityIsNull() {
        User userIsNull = null;
        testedDao.delete(userIsNull);
    }

    @Test(expected = DAOException.class)
    public void failsIfTableDoesNotExist() {
        User existedUser = getExpectedEntity();
        dropTablesInTestDb();
        testedDao.delete(existedUser);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteEntityDoesNotExistInDb() {
        User notExistedUser = createEntity();
        notExistedUser.setId(NOT_EXISTING_ENTITY_KEY);
        testedDao.delete(notExistedUser);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasId_0() {
        User notExistedUser = createEntity();
        notExistedUser.setId(0L);
        testedDao.delete(notExistedUser);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasIdWhichLessThan_0() {
        User notExistedUser = createEntity();
        notExistedUser.setId(-3L);
        testedDao.delete(notExistedUser);
    }

    @Test(expected = DAOException.class)
    public void failsOnDeleteIfEntityHasIdWith_null_Value() {
        User notExistedUser = createEntity();
        notExistedUser.setId(null);
        testedDao.delete(notExistedUser);
    }

    @Test
    public void resultIs_1_OnDeleteIfEntitysIdExists() {
        User notExistedUser = createEntity();
        notExistedUser.setId(2L);
        List<User> rolesListBefore = testedDao.getAll().orElse(null);
        testedDao.delete(notExistedUser);
        List<User> rolesListAfter = testedDao.getAll().orElse(null);
        assertNotNull(rolesListBefore);
        assertNotNull(rolesListAfter);
        assertEquals(1, rolesListBefore.size() - rolesListAfter.size());
    }
}
