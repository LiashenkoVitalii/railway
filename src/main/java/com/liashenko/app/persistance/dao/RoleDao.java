package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(Role object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<Role> persist(Role object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<Role> getByPK(Long key);

    //Saves object's state to db
    void update(Role object);

    //Deletes the row with PK corresponds to object's id
    void delete(Role object);

    //Returns all rows from table
    Optional<List<Role>> getAll();
}
