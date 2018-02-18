package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.RouteRate;

import java.util.List;
import java.util.Optional;

/*
 *
 */

public interface RouteRateDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(RouteRate object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<RouteRate> persist(RouteRate object);

    //Returns an object corresponds to row with primary key or empty optional
    Optional<RouteRate> getByPK(Long key);

    //Saves object's state to db
    void update(RouteRate object);

    //Deletes the row with PK corresponds to object's id
    void delete(RouteRate object);

    //Returns all rows from table
    Optional<List<RouteRate>> getAll();

    //Returns the row corresponds to FK
    Optional<RouteRate> getByRouteId(Long routeId);
}
