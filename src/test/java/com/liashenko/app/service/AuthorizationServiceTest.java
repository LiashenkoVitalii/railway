package com.liashenko.app.service;

import com.liashenko.app.service.dto.PrinciplesDto;
import com.liashenko.app.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;

@RunWith(value = Parameterized.class)
public class AuthorizationServiceTest extends TestDbUtil {

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private AuthorizationService testedService;

    public AuthorizationServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getAuthorizationService(localeBundle);
    }

    //    Optional<UserSessionProfileDto> logIn(PrinciplesDto principlesDto);
    @Test(expected = ServiceException.class)
    public void failsIfEmailDoesNotExist() {
        PrinciplesDto testDataEntity = new PrinciplesDto("@mail.com", "aa".toCharArray());
        testedService.logIn(testDataEntity);
    }

    @Test
    public void returnsEmptyOptionalIfPasswordDoesNotExist() {
        PrinciplesDto testDataEntity = new PrinciplesDto("user@mail.com", "....".toCharArray());
        assertFalse(testedService.logIn(testDataEntity).isPresent());
    }

    @Test(expected = ServiceException.class)
    public void returnsEmptyOptionalIfPrincipalsDtoIsNull() {
        testedService.logIn(null);
    }
}
