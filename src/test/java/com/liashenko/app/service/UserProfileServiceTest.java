package com.liashenko.app.service;

import com.liashenko.app.service.dto.UserDto;
import com.liashenko.app.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.util.ResourceBundle;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class UserProfileServiceTest extends TestDbUtil {

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private UserProfileService testedService;

    public UserProfileServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getUserProfileService(localeBundle);
    }

    //    void createProfile(UserDto userDto);
    @Test(expected = ServiceException.class)
    public void failsIfUserDtoIsNull() {
        testedService.createProfile(null);
    }

    //    void changeLanguage(Long userId, String newLanguage);
    @Test(expected = ServiceException.class)
    public void failsIfUserIdDoesNotExists() {
        testedService.changeLanguage(0L, "uk_UA");
    }

    //  boolean isEmailExists(String email);
    @Test
    public void returnsFalseIfEmailExists() {
        assertFalse(testedService.isEmailExists("user@user.com"));
    }

    //  Optional<UserDto> getUserById(Long userId);
    @Test
    public void returnsExpectedEntityByExistingId() {
        UserDto expectedEntity = UserDto.builder().firstName("user").lastName("user").email("user@mail.com").build();
        UserDto actualEntity = testedService.getUserById(25L).orElse(null);
        assertNotNull(actualEntity);
        assertEquals(expectedEntity, actualEntity);
    }

    //    void updateProfile(UserDto userDto);
    @Test(expected = ServiceException.class)
    public void methodFailsIfUserDtoIsNull() {
        testedService.updateProfile(null);
    }

    //  boolean isOtherUsersWithEmailExist(Long userId, String email);
    @Test
    public void returnsFalseIfOtherUserWithEmailExists() {
        assertFalse(testedService.isOtherUsersWithEmailExist(25L, "user@user.com"));
    }
}
