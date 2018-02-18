package com.liashenko.app.service;

import com.liashenko.app.service.dto.RoleDto;
import com.liashenko.app.service.dto.UserDto;
import com.liashenko.app.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static junit.framework.TestCase.*;

@RunWith(value = Parameterized.class)
public class AdminServiceTest extends TestDbUtil {

    private static final int EXPECTED_USERS_COUNT = 25;
    private static final int DEFAULT_USERS_COUNT_ON_PAGE = 5;

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private AdminService testedService;

    public AdminServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getAdminService(localeBundle);
    }

    //    Optional<List<UserDto>> showUsers(int rowsPerPage, int offset);
    @Test
    public void failsIf_rowsPerPage_Is_0() {
        Optional<List<UserDto>> usersOpt = testedService.showUsers(0, 5);
        int actualUsersCount = 0;
        if (usersOpt.isPresent()) {
            actualUsersCount = usersOpt.get().size();
        }
        assertEquals(EXPECTED_USERS_COUNT, actualUsersCount);
    }

    @Test(expected = ServiceException.class)
    public void failsIf_rowsPerPage_IsLessThan_0() {
        testedService.showUsers(-3, 5);
    }

    @Test(expected = ServiceException.class)
    public void failsIf_offset_IsLessThan_0() {
        testedService.showUsers(DEFAULT_USERS_COUNT_ON_PAGE, -1);
    }

    @Test
    public void returnsPagesWithUsers() {
        assertTrue(testedService.showUsers(DEFAULT_USERS_COUNT_ON_PAGE, 5).isPresent());
    }

    //    Optional<List<RoleDto>> showRoles();
    @Test
    public void returnsExpectedListOfRoles() {
        List<RoleDto> expectedList = createExpectedRoleList();
        List<RoleDto> actualList = testedService.showRoles().orElse(null);
        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    private List<RoleDto> createExpectedRoleList() {
        List<RoleDto> expectedList = new ArrayList<>();
        RoleDto adminRole = new RoleDto(1L, "Адміністратор");
        RoleDto userDto = new RoleDto(2L, "Користувач");

        if (localeBundle.getLocale().getLanguage().equals("en")) {
            adminRole.setName("Administrator");
            userDto.setName("User");
        } else {
            adminRole.setName("Адміністратор");
            userDto.setName("Користувач");
        }
        expectedList.add(adminRole);
        expectedList.add(userDto);
        return expectedList;
    }

    //    void updateUserInfo(UserDto userDto);
    @Test(expected = ServiceException.class)
    public void failsIfUserDtoIsNull() {
        testedService.updateUserInfo(null);
    }

    //    Integer getUsersCount();
    @Test
    public void returnsExpectedValue() {
        assertEquals(EXPECTED_USERS_COUNT, (int) testedService.getUsersCount());
    }
}
