package com.liashenko.app.service;

import com.liashenko.app.service.dto.AutocompleteDto;
import com.liashenko.app.service.dto.RouteDto;
import com.liashenko.app.service.dto.TrainDto;

import java.util.List;
import java.util.Optional;

//Interface contains methods to provide searching trains and stations using entered by user data
public interface TrainSearchingService {

    Optional<List<AutocompleteDto>> getStationAutocomplete(String stationLike);

    Optional<List<TrainDto>> getTrainsForTheRouteOnDate(RouteDto routeDto);
}
