package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.PricePerKmForVagon;

import java.util.List;
import java.util.Optional;

public interface PricePerKmForVagonDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);


    //Creates new row in the db corresponds to its object
    void create(PricePerKmForVagon object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<PricePerKmForVagon> persist(PricePerKmForVagon object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<PricePerKmForVagon> getByPK(Long key);

    //Saves object's state to db
    void update(PricePerKmForVagon object);

    //Deletes the row with PK corresponds to object's id
    void delete(PricePerKmForVagon object);

    //Returns all rows from table
    Optional<List<PricePerKmForVagon>> getAll();

    Optional<PricePerKmForVagon> getPricePerKmForVagon(Integer vagonTypeId);
}
