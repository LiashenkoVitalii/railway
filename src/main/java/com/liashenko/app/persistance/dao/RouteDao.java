package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.Route;

import java.util.List;
import java.util.Optional;

public interface RouteDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(Route object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<Route> persist(Route object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<Route> getByPK(Long key);

    //Saves object's state to db
    void update(Route object);

    //Deletes the row with PK corresponds to object's id
    void delete(Route object);

    //Returns all rows from table
    Optional<List<Route>> getAll();

    //Returns routes with the specified stations taking into account their order by distance
    Optional<List<Route>> getRoutesByDepartureAndArrivalStationsId(Long departureStationId, Long arrivalStationId);

    Optional<Route> getFirstTerminalStationOnRoute(Long routeId);

    Optional<Route> getLastTerminalStationOnRoute(Long routeId);

    //Returns details for the specified station belongs to the specified route
    Optional<Route> getStationOnRoute(Long stationId, Long routeId);
}
