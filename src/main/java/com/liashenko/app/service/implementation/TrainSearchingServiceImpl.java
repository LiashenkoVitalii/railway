package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.*;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.Route;
import com.liashenko.app.persistance.domain.TimeTable;
import com.liashenko.app.service.TrainSearchingService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.AutocompleteDto;
import com.liashenko.app.service.dto.RouteDto;
import com.liashenko.app.service.dto.TrainDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.web.controller.utils.HttpParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.liashenko.app.utils.Asserts.assertIsNull;

public class TrainSearchingServiceImpl implements TrainSearchingService {
    private static final Logger classLogger = LogManager.getLogger(UserProfileServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnServ;
    private DaoFactory daoFactory;

    public TrainSearchingServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnServ = dbConnSrvc;
    }

    @Override
    public Optional<List<AutocompleteDto>> getStationAutocomplete(String stationLike) {
        List<AutocompleteDto> autocompleteWordList = new ArrayList<>();
        Connection conn = dbConnServ.getConnection();
        try {
            StationDao stationDao = daoFactory.getStationDao(conn, localeQueries);
            stationDao.getStationsLike(stationLike).ifPresent(stations
                    -> stations.forEach(station
                    -> autocompleteWordList.add(new AutocompleteDto(station.getId(), station.getName()))));

        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnServ.close(conn);
        }
        return Optional.of(autocompleteWordList);
    }

    @Override
    public Optional<List<TrainDto>> getTrainsForTheRouteOnDate(RouteDto routeDto) {
        if (assertIsNull(routeDto)) throw new ServiceException("routeDao is null");
        Connection conn = dbConnServ.getConnection();
        List<TrainDto> trainDtoList = null;
        try {
            RouteDao routeDao = daoFactory.getRouteDao(conn, localeQueries);
            setStationNamesById(routeDto, conn);
            Optional<List<Route>> routesOpt = routeDao.getRoutesByDepartureAndArrivalStationsId(routeDto.getFromStationId(),
                    routeDto.getToStationId());
            if (routesOpt.isPresent()) {
                trainDtoList = new ArrayList<>();
                List<Route> routs = routesOpt.get();
                for (Route route : routs) {
                    TrainDto trainDto = new TrainDto();
                    setRoutWithFromAndToStationsForTrain(routeDto.getFromStationId(), routeDto.getFromStationName(),
                            routeDto.getToStationId(), routeDto.getToStationName(), route, trainDto);
                    setTrainIdAndNumber(conn, route, trainDto);
                    setLeavingAndArrivalDatesForTrain(routeDto.getFromStationId(), routeDto.getToStationId(),
                            routeDto.getDateString(), conn, trainDtoList, route, trainDto);
                }
            }
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnServ.close(conn);
        }
        return Optional.ofNullable(trainDtoList);
    }

    //this method would be unneeded and should be removed
    //if only you could figure out why we get wrong encoding(Cp1251 - on localhost) from client
    //(the issue exist only when the project runs without IDE)
    private void setStationNamesById(RouteDto routeDto, Connection conn) {
        StationDao stationDao = daoFactory.getStationDao(conn, localeQueries);
        stationDao.getByPK(routeDto.getFromStationId())
                .ifPresent(station -> routeDto.setFromStationName(station.getName()));
        stationDao.getByPK(routeDto.getToStationId())
                .ifPresent(station -> routeDto.setToStationName(station.getName()));
    }

    private void setTrainIdAndNumber(Connection conn, Route route, TrainDto trainDto) {
        TrainDao trainDao = daoFactory.getTrainDao(conn, localeQueries);
        trainDao.getByRoute(route.getRouteNumberId())
                .ifPresent(train -> {
                    trainDto.setTrainNumber(train.getNumber());
                    trainDto.setTrainId(train.getId());
                });
    }

    private void setRoutWithFromAndToStationsForTrain(Long stationFromId, String stationFromName, Long stationToId,
                                                      String stationToName, Route route, TrainDto trainDto) {
        trainDto.setStationFromId(stationFromId);
        trainDto.setStationToId(stationToId);
        trainDto.setFromStation(stationFromName);
        trainDto.setToStation(stationToName);
        trainDto.setRouteId(route.getRouteNumberId());
    }

    private void setLeavingAndArrivalDatesForTrain(Long stationFromId, Long stationToId, String dateStr,
                                                   Connection conn, List<TrainDto> trainDtoList,
                                                   Route route, TrainDto trainDto) {

        LocalDate requiredDate = HttpParser.convertStringToDate(dateStr);
        //retrieving arrival and departure time from timetable
        TimeTableDao timeTableDao = daoFactory.getTimeTableDao(conn, localeQueries);
        Optional<TimeTable> leavingTimeTableOpt = timeTableDao
                .getTimeTableForStationByDataAndRoute(stationFromId, route.getRouteNumberId(), requiredDate);
        Optional<TimeTable> arrivalTimeTableOpt = timeTableDao
                .getTimeTableForStationByDataAndRoute(stationToId, route.getRouteNumberId(), requiredDate);

        //calculate arrival and departure time to required date
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

            trainDto.setLeavingDate(HttpParser.convertDateTimeToHumanReadableString(leavingTimeForRequiredDate));
            trainDto.setArrivalDate(HttpParser.convertDateTimeToHumanReadableString(arrivalTimeForRequiredDate));
            trainDtoList.add(trainDto);
        }
    }
}
