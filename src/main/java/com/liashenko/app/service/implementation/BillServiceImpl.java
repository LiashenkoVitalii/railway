package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.*;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.PricePerKmForVagon;
import com.liashenko.app.persistance.domain.RouteRate;
import com.liashenko.app.persistance.domain.TimeTable;
import com.liashenko.app.service.BillService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.BillDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.utils.AppProperties;
import com.liashenko.app.web.controller.utils.HttpParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;

public class BillServiceImpl implements BillService {
    private static final Logger classLogger = LogManager.getLogger(BillServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnSrvc;
    private DaoFactory daoFactory;

    public BillServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnSrvc = dbConnSrvc;
    }

    @Override
    public Optional<BillDto> showBill(Long routeId, Long trainId, Long fromStationId, Long toStationId, String trainName,
                                      String firstName, String lastName, Integer vagonTypeId, String date) {
        LocalDate localDate = HttpParser.convertStringToDate(date);
        BillDto billDto = new BillDto();
        Connection conn = dbConnSrvc.getConnection();
        try {
            setFirstAndLastName(billDto, firstName, lastName);
            setStationsAndCity(billDto, fromStationId, toStationId, conn);
            setTrainNameAndVagonNumber(billDto, trainId, trainName, conn);
            setVagonTypeAndPlace(billDto, vagonTypeId, conn);
            setTicketNumberAndDate(billDto);
            setLeavingAndArrivalDate(billDto, fromStationId, toStationId, localDate, routeId, conn);
            setPrice(billDto, routeId, conn, fromStationId, toStationId);
            if (!isAllFieldsFilled(billDto)) throw new ServiceException("Not all fields are filled");
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.of(billDto);
    }

    private void setFirstAndLastName(BillDto billDto, String firstName, String lastName) {
        billDto.setFirstName(firstName);
        billDto.setLastName(lastName);
    }

    private void setStationsAndCity(BillDto billDto, Long fromStationId, Long toStationId, Connection connection) {
        billDto.setFromStationId(fromStationId);
        billDto.setToStationId(toStationId);
        StationDao stationDao = daoFactory.getStationDao(connection, localeQueries);

        stationDao.getByPK(fromStationId).ifPresent(station -> {
            billDto.setFromCityName(station.getCity());
            billDto.setFromStationName(station.getName());
        });

        stationDao.getByPK(toStationId).ifPresent(station
                -> billDto.setToStationName(station.getName()));
    }

    private void setTrainNameAndVagonNumber(BillDto billDto, Long trainId, String trainName, Connection conn) {
        billDto.setTrainNumber(trainName);
        TrainDao trainDao = daoFactory.getTrainDao(conn, localeQueries);
        trainDao.getByPK(trainId).ifPresent(train -> billDto.setVagonNumber(CalculatorUtil.generateValue(train.getVagonCount())));
    }

    private void setVagonTypeAndPlace(BillDto billDto, Integer vagonId, Connection connection) {
        VagonTypeDao vagonTypeDao = daoFactory.getVagonTypeDao(connection, localeQueries);

        vagonTypeDao.getByPK(vagonId).ifPresent(vagonType -> {
            billDto.setVagonTypeName(vagonType.getTypeName());
            billDto.setVagonTypeId(vagonType.getId());
            billDto.setPlaceNumber(CalculatorUtil.generateValue(vagonType.getPlacesCount()));
        });
    }

    private void setTicketNumberAndDate(BillDto billDto) {
        billDto.setTicketNumber(String.format("%06d", CalculatorUtil.generateValue(AppProperties.getMaxTicketNumber())));
        billDto.setTicketDate(HttpParser.convertDateToHumanReadableString(LocalDateTime.now().toLocalDate()));
    }

    private void setLeavingAndArrivalDate(BillDto billDto, Long fromStationId, Long toStationId, LocalDate requiredDate,
                                          Long routeId, Connection conn) {

        //retrieving arrival and departure time from timetable
        TimeTableDao timeTableDao = daoFactory.getTimeTableDao(conn, localeQueries);
        Optional<TimeTable> leavingTimeTableOpt = timeTableDao
                .getTimeTableForStationByDataAndRoute(fromStationId, routeId, requiredDate);
        Optional<TimeTable> arrivalTimeTableOpt = timeTableDao
                .getTimeTableForStationByDataAndRoute(toStationId, routeId, requiredDate);

        //converting arrival and departure time to required date
        if (arrivalTimeTableOpt.isPresent() && leavingTimeTableOpt.isPresent()) {
            LocalDateTime leavingTime = leavingTimeTableOpt.get().getDeparture();
            LocalDateTime arrivalTime = arrivalTimeTableOpt.get().getArrival();

            LocalDateTime leavingTimeForRequiredDate = leavingTime
                    .withDayOfMonth(requiredDate.getDayOfMonth())
                    .withMonth(requiredDate.getMonthValue())
                    .withYear(requiredDate.getYear());

            LocalDateTime arrivalTimeForRequiredDate = leavingTimeForRequiredDate
                    .plusDays(Period.between(leavingTime.toLocalDate(), arrivalTime.toLocalDate()).getDays())
                    .withHour(arrivalTime.getHour())
                    .withMinute(arrivalTime.getMinute())
                    .withSecond(arrivalTime.getSecond());

            billDto.setFromStationLeavingDate(HttpParser.convertDateTimeToHumanReadableString(leavingTimeForRequiredDate));
            billDto.setToStationArrivalDate(HttpParser.convertDateTimeToHumanReadableString(arrivalTimeForRequiredDate));
        }
    }

    private void setPrice(BillDto billDto, Long routeId, Connection conn, Long fromStationId, Long toStationId) {
        RouteDao routeDao = daoFactory.getRouteDao(conn, localeQueries);
        Float distance = CalculatorUtil.calculateDistanceFromStationToStationOnTheRoute(routeDao, routeId,
                fromStationId, toStationId);

        RouteRateDao routeRateDao = daoFactory.getRouteRateDao(conn, localeQueries);
        Optional<RouteRate> routeRateOpt = routeRateDao.getByRouteId(routeId);
        Float routeRateFloat = routeRateOpt.isPresent() ? routeRateOpt.get().getRate() : AppProperties.getDefRouteRate();

        VagonTypeDao vagonTypeDao = daoFactory.getVagonTypeDao(conn, localeQueries);
        Integer placesCount = vagonTypeDao.getByPK(billDto.getVagonTypeId())
                .orElseThrow(() -> new ServiceException("VagonType is null")).getPlacesCount();

        PricePerKmForVagonDao pricePerKmForVagonDao = daoFactory.getPricePerKmForVagonDao(conn, localeQueries);
        Optional<PricePerKmForVagon> pricePerKmForVagonOpt = pricePerKmForVagonDao.getPricePerKmForVagon(billDto.getVagonTypeId());

        Double pricePerKmForVagonDouble = pricePerKmForVagonOpt.isPresent() ? pricePerKmForVagonOpt.get().getPrice()
                : AppProperties.getDefPriceForVagonKm();
        Float price = CalculatorUtil.calculateTicketPrice(routeRateFloat, distance, pricePerKmForVagonDouble, placesCount);
        billDto.setTicketPrice(price);
    }

    private boolean isAllFieldsFilled(BillDto billDto) {
        if (assertIsNull(billDto.getFirstName())) return false;
        if (assertIsNull(billDto.getLastName())) return false;
        if (assertIsNull(billDto.getFromStationName())) return false;
        if (assertIsNull(billDto.getFromStationId())) return false;
        if (assertIsNull(billDto.getToStationId())) return false;
        if (assertIsNull(billDto.getFromCityName())) return false;
        if (assertIsNull(billDto.getFromStationLeavingDate())) return false;
        if (assertIsNull(billDto.getToStationArrivalDate())) return false;
        if (assertIsNull(billDto.getVagonTypeName())) return false;
        if (assertIsNull(billDto.getVagonTypeId())) return false;
        if (assertIsNull(billDto.getVagonNumber())) return false;
        if (assertIsNull(billDto.getTrainNumber())) return false;
        if (assertIsNull(billDto.getPlaceNumber())) return false;
        if (assertIsNull(billDto.getTicketPrice())) return false;
        if (assertIsNull(billDto.getTicketNumber())) return false;
        if (assertIsNull(billDto.getTicketDate())) return false;
        return true;
    }
}
