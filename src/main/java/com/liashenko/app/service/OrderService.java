package com.liashenko.app.service;

import com.liashenko.app.service.dto.FullRouteDto;
import com.liashenko.app.service.dto.PriceForVagonDto;

import java.util.List;
import java.util.Optional;

//Contains methods to process entered by user info about ordering the ticket
public interface OrderService {

    //Returns route details by routeId
    Optional<FullRouteDto> getFullTrainRoute(Long routeId);

    Optional<List<PriceForVagonDto>> getPricesForVagons(Long fromStationId, Long toStationId, Long routeId);

    Optional<String> getStationNameById(Long stationId);
}
