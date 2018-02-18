package com.liashenko.app.service;

import com.liashenko.app.service.dto.BillDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test_utils.DbInitFixtures;
import test_utils.TestDbUtil;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(value = Parameterized.class)
public class BillServiceTest extends TestDbUtil {
    @Rule
    public DbInitFixtures dbInitFixtures = new DbInitFixtures();
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ResourceBundle localeBundle;
    private BillService testedService;

    public BillServiceTest(ResourceBundle localeBundle) {
        this.localeBundle = localeBundle;
    }

    @Before
    public void setUpService() {
        this.testedService = serviceFactory.getBillService(localeBundle);
    }

    //    Optional<BillDto> showBill(Long routeId, Long trainId, Long fromStationId, Long toStationId, String trainName,
//                               String firstName, String lastName, Integer vagonTypeId, String date);
    @Test
    public void returnsExpectedBillDto() {
        BillDto expectedEntity = createExpectedEntity();
        BillDto actualEntity = testedService.showBill(1L, 1L, 1L, 14L,
                "776П", "Віталій", "Ляшенко", 3, "20.01.2018")
                .orElse(null);
        System.out.println("BillServiceTest.returnsExpectedBillDto: " + actualEntity);
        assertNotNull(actualEntity);
        assertEquals(expectedEntity.getToStationId(), actualEntity.getToStationId());
        assertEquals(expectedEntity.getFromCityName(), actualEntity.getFromCityName());
        assertEquals(expectedEntity.getTicketPrice(), actualEntity.getTicketPrice());
        assertEquals(expectedEntity.getVagonTypeId(), actualEntity.getVagonTypeId());
        assertEquals(expectedEntity.getVagonTypeName(), actualEntity.getVagonTypeName());
        assertEquals(expectedEntity.getFirstName(), actualEntity.getFirstName());
        assertEquals(expectedEntity.getLastName(), actualEntity.getLastName());
        assertEquals(expectedEntity.getFromStationName(), actualEntity.getFromStationName());
        assertEquals(expectedEntity.getToStationName(), actualEntity.getToStationName());
        assertEquals(expectedEntity.getToStationId(), actualEntity.getToStationId());
        assertEquals(expectedEntity.getFromCityName(), actualEntity.getFromCityName());
        assertEquals(expectedEntity.getFromStationLeavingDate(), actualEntity.getFromStationLeavingDate());
        assertEquals(expectedEntity.getToStationArrivalDate(), actualEntity.getToStationArrivalDate());
        assertEquals(expectedEntity.getTrainNumber(), actualEntity.getTrainNumber());

        assertNotNull(actualEntity.getTicketDate());
        assertNotNull(actualEntity.getPlaceNumber());
        assertNotNull(actualEntity.getVagonNumber());
        assertNotNull(actualEntity.getTrainNumber());
    }


    private BillDto createExpectedEntity() {
        BillDto billDto = new BillDto();

        billDto.setToStationId(14L);
        billDto.setFromStationId(1L);
        billDto.setTicketPrice(1249.14F);
        billDto.setVagonTypeId(3);

        billDto.setFromStationLeavingDate("00.22 20.01.2018");
        billDto.setToStationArrivalDate("07.14 20.01.2018");
        billDto.setTrainNumber("776П");
        billDto.setFirstName("Віталій");
        billDto.setLastName("Ляшенко");

        if (localeBundle.getLocale().getLanguage().equals("en")) {
            billDto.setFromStationName("Kyiv-Pasazhyrs'kyi");
            billDto.setToStationName("Kharkiv-Pasazhyrs'kyi");
            billDto.setFromCityName("Kyiv");
            billDto.setVagonTypeName("Lux");
        } else {
            billDto.setFromStationName("Київ-Пасажирський");
            billDto.setToStationName("Харків-Пасажирський");
            billDto.setFromCityName("Київ");
            billDto.setVagonTypeName("Люкс");
        }

        return billDto;
    }
}
