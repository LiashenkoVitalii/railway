package com.liashenko.app.service;

import com.liashenko.app.service.dto.FullRouteDto;
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
public class OrderServiceTest extends TestDbUtil {

    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private OrderService testedService;

    public OrderServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getOrderService(localeBundle);
    }

    //    Optional<FullRouteDto> getFullTrainRoute(Long routeId);
    @Test
    public void returnsExpectedEntity() {
        FullRouteDto expectedEntity;
        if (localeBundle.getLocale().getLanguage().equals("en")) {
            expectedEntity = new FullRouteDto("Kyiv", "Kharkiv");
        } else {
            expectedEntity = new FullRouteDto("Київ", "Харків");
        }

        FullRouteDto actualEntity = testedService.getFullTrainRoute(1L).orElse(null);
        assertNotNull(actualEntity);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test(expected = ServiceException.class)
    public void failsIfRouteDoesNotExists() {
        testedService.getFullTrainRoute(15L);

    }

    //    Optional<List<PriceForVagonDto>> getPricesForVagons(Long fromStationId, Long toStationId, Long routeId);
    @Test(expected = ServiceException.class)
    public void failsIfRoutIdDoesNotExist() {
        assertFalse(testedService.getPricesForVagons(1L, 14L, 15L).isPresent());
    }

    //    Optional<String> getStationNameById(Long stationId);
    @Test
    public void returnsExpectedName() {
        String expectedName;
        if (localeBundle.getLocale().getLanguage().equals("en")) {
            expectedName = "Kharkiv-Pasazhyrs'kyi";
        } else {
            expectedName = "Харків-Пасажирський";
        }
        String actualName = testedService.getStationNameById(14L).orElse(null);
        assertNotNull(actualName);
        assertEquals(expectedName, actualName);
    }
}
