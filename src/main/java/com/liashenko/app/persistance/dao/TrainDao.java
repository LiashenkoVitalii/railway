package com.liashenko.app.persistance.dao;


import com.liashenko.app.persistance.domain.Train;

import java.util.List;
import java.util.Optional;

public interface TrainDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(Train object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<Train> persist(Train object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<Train> getByPK(Long key);

    //Saves object's state to db
    void update(Train object);

    //Deletes the row with PK corresponds to object's id
    void delete(Train object);

    //Returns all rows from table
    Optional<List<Train>> getAll();

    //Returned train by its route
    Optional<Train> getByRoute(Long routeId);
}
