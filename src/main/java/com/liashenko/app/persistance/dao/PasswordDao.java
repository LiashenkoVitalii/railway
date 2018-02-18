package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.Password;

import java.util.List;
import java.util.Optional;

public interface PasswordDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(Password object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<Password> persist(Password object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<Password> getByPK(Long key);

    //Saves object's state to db
    void update(Password object);

    //Deletes the row with PK corresponds to object's id
    void delete(Password object);

    //Returns all rows from table
    Optional<List<Password>> getAll();
}
