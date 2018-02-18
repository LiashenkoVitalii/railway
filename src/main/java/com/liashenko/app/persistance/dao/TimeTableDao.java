package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.TimeTable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimeTableDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(TimeTable object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<TimeTable> persist(TimeTable object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<TimeTable> getByPK(Long key);

    //Saves object's state to db
    void update(TimeTable object);

    //Deletes the row with PK corresponds to object's id
    void delete(TimeTable object);

    //Returns all rows from table
    Optional<List<TimeTable>> getAll();

    Optional<TimeTable> getTimeTableForStationByDataAndRoute(Long departureStationId, Long routeId, LocalDate date);
}
