package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.VagonType;

import java.util.List;
import java.util.Optional;

public interface VagonTypeDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(VagonType object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<VagonType> persist(VagonType object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<VagonType> getByPK(Integer key);

    //Saves object's state to db
    void update(VagonType object);

    //Deletes the row with PK corresponds to object's id
    void delete(VagonType object);

    //Returns all rows from table
    Optional<List<VagonType>> getAll();
}
