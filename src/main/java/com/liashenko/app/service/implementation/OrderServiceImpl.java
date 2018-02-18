package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.*;
import com.liashenko.app.persistance.dao.exceptions.DAOException;
import com.liashenko.app.persistance.domain.*;
import com.liashenko.app.service.OrderService;
import com.liashenko.app.service.data_source.DbConnectionService;
import com.liashenko.app.service.dto.FullRouteDto;
import com.liashenko.app.service.dto.PriceForVagonDto;
import com.liashenko.app.service.exceptions.ServiceException;
import com.liashenko.app.utils.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderServiceImpl implements OrderService {
    private static final Logger classLogger = LogManager.getLogger(OrderServiceImpl.class);

    private ResourceBundle localeQueries;
    private DbConnectionService dbConnSrvc;
    private DaoFactory daoFactory;

    public OrderServiceImpl(ResourceBundle localeQueries, DaoFactory daoFactory, DbConnectionService dbConnSrvc) {
        this.localeQueries = localeQueries;
        this.daoFactory = daoFactory;
        this.dbConnSrvc = dbConnSrvc;
    }

    public OrderServiceImpl(ResourceBundle localeQueries) {
        this.localeQueries = localeQueries;
    }

    @Override
    public Optional<FullRouteDto> getFullTrainRoute(Long routeId) {
        Connection conn = dbConnSrvc.getConnection();
        try {
            RouteDao routeDao = daoFactory.getRouteDao(conn, localeQueries);
            Optional<Route> routeFirstStationOpt = routeDao.getFirstTerminalStationOnRoute(routeId);
            Optional<Route> routeLastStationOpt = routeDao.getLastTerminalStationOnRoute(routeId);

            if (routeFirstStationOpt.isPresent() && routeLastStationOpt.isPresent()) {
                Long routeFirstStationId = routeFirstStationOpt.get().getStationId();
                Long routeLastStationId = routeLastStationOpt.get().getStationId();
                return Optional.ofNullable(getRouteWithTerminalStations(conn, routeFirstStationId, routeLastStationId));
            } else {
                throw new ServiceException("Couldn't find terminal station id for the route ");
            }
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }

    private FullRouteDto getRouteWithTerminalStations(Connection connection, Long firstStationId, Long lastStationId) {
        StationDao stationDao = daoFactory.getStationDao(connection, localeQueries);
        Optional<Station> firstStationOpt = stationDao.getByPK(firstStationId);
        Optional<Station> lastStationOpt = stationDao.getByPK(lastStationId);
        if (firstStationOpt.isPresent() && lastStationOpt.isPresent()) {
            return new FullRouteDto(firstStationOpt.get().getCity(), lastStationOpt.get().getCity());
        } else {
            throw new ServiceException("Couldn't find terminal station's city for the route ");
        }
    }

    @Override
    public Optional<List<PriceForVagonDto>> getPricesForVagons(Long fromStationId, Long toStationId, Long routeId) {
        List<PriceForVagonDto> pricesForVagonList = new ArrayList<>();
        Connection conn = dbConnSrvc.getConnection();
        try {
            RouteDao routeDao = daoFactory.getRouteDao(conn, localeQueries);
            Float distance = CalculatorUtil.calculateDistanceFromStationToStationOnTheRoute(routeDao, routeId,
                    fromStationId, toStationId);

            RouteRateDao routeRateDao = daoFactory.getRouteRateDao(conn, localeQueries);
            Optional<RouteRate> routeRateOpt = routeRateDao.getByRouteId(routeId);
            Float routeRateFloat = routeRateOpt.isPresent() ? routeRateOpt.get().getRate()
                    : AppProperties.getDefRouteRate();

            VagonTypeDao vagonTypeDao = daoFactory.getVagonTypeDao(conn, localeQueries);
            Optional<List<VagonType>> vagonTypeListOpt = vagonTypeDao.getAll();
            vagonTypeListOpt.ifPresent(vagonTypes
                    -> vagonTypes.forEach(vagonType
                    -> calculateAndFillListOfPricesForVagons(conn, pricesForVagonList, vagonType,
                    distance, routeRateFloat)));

        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
        return Optional.of(pricesForVagonList);
    }

    private void calculateAndFillListOfPricesForVagons(Connection connection, List<PriceForVagonDto> pricesForVagonList,
                                                       VagonType vagonType, Float distance, Float routeRateFloat) {
        PriceForVagonDto priceForVagonDto = new PriceForVagonDto();
        PricePerKmForVagonDao pricePerKmForVagonDao = daoFactory.getPricePerKmForVagonDao(connection, localeQueries);
        Optional<PricePerKmForVagon> pricePerKmForVagonOpt = pricePerKmForVagonDao.getPricePerKmForVagon(vagonType.getId());
        Double pricePerKmForVagonDouble = pricePerKmForVagonOpt.isPresent()
                ? pricePerKmForVagonOpt.get().getPrice()
                : AppProperties.getDefPriceForVagonKm();

        Float price = CalculatorUtil.calculateTicketPrice(routeRateFloat, distance, pricePerKmForVagonDouble,
                vagonType.getPlacesCount());
        priceForVagonDto.setVagonTypeId(vagonType.getId());
        priceForVagonDto.setVagonTypeName(vagonType.getTypeName());
        priceForVagonDto.setDistance(distance);
        priceForVagonDto.setTicketPrice(price);
        pricesForVagonList.add(priceForVagonDto);
    }

    @Override
    public Optional<String> getStationNameById(Long stationId) {
        Connection conn = dbConnSrvc.getConnection();
        try {
//            conn.setReadOnly(true);
            StationDao stationDao = daoFactory.getStationDao(conn, localeQueries);
            Optional<Station> stationOpt = stationDao.getByPK(stationId);
            return stationOpt.isPresent() ? Optional.ofNullable(stationOpt.get().getName()) : Optional.empty();
        } catch (ServiceException | DAOException e) {
            classLogger.error(e);
            throw new ServiceException(e);
        } finally {
            dbConnSrvc.close(conn);
        }
    }
}
