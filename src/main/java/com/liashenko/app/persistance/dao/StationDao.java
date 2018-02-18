package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.Station;

import java.util.List;
import java.util.Optional;

public interface StationDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(Station object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<Station> persist(Station object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<Station> getByPK(Long key);

    //Saves object's state to db
    void update(Station object);

    //Deletes the row with PK corresponds to object's id
    void delete(Station object);

    //Returns all rows from table
    Optional<List<Station>> getAll();

    //Returns stations correspond to the specified pattern
    Optional<List<Station>> getStationsLike(String likePattern);
}
