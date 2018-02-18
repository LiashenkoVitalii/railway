package com.liashenko.app.service.implementation;

import com.liashenko.app.persistance.dao.RouteDao;
import com.liashenko.app.persistance.domain.Route;
import com.liashenko.app.service.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

//Class deigned to perform routine calculations from service layer
public abstract class CalculatorUtil {
    private static final int SIGNS_AFTER_ZERO_IN_TICKET_PRICE = 2;
    private static final Random RANDOM = new Random();

    public static Float calculateDistanceFromStationToStationOnTheRoute(RouteDao routeDao, Long routeId,
                                                                        Long fromStationId, Long toStationId) {
        Float distance;
        Optional<Route> fromStation = routeDao.getStationOnRoute(fromStationId, routeId);
        Optional<Route> toStation = routeDao.getStationOnRoute(toStationId, routeId);

        if (fromStation.isPresent() && toStation.isPresent()) {
            distance = toStation.get().getDistance() - fromStation.get().getDistance();
            if (distance <= 0F) {
                throw new ServiceException("Distance between stations is less than zero");
            }
        } else {
            throw new ServiceException("Couldn't calculate distance between the stations");
        }
        return distance;
    }

    public static Float calculateTicketPrice(Float routeRateFloat, Float distance, Double pricePerKmForVagonDouble,
                                             Integer placesCount) {
        return BigDecimal.valueOf(routeRateFloat)
                .multiply(BigDecimal.valueOf(distance))
                .multiply(BigDecimal.valueOf(pricePerKmForVagonDouble))
                .divide(BigDecimal.valueOf(placesCount), SIGNS_AFTER_ZERO_IN_TICKET_PRICE, BigDecimal.ROUND_HALF_UP)
                .floatValue();
    }

    //returns random positive value from 0 to maxValueCount
    public static int generateValue(int maxValueCount) {
        int res = RANDOM.nextInt(maxValueCount);
        return (res < 0) ? -res : res;
    }
}
