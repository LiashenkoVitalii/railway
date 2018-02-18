package com.liashenko.app.persistance.dao;

import com.liashenko.app.persistance.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericJDBCDao {

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(Long key);

    //Creates new row in the db corresponds to its object
    void create(User object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<User> persist(User object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<User> getByPK(Long key);

    //Saves object's state to db
    void update(User object);

    //Deletes the row with PK corresponds to object's id
    void delete(User object);

    //Returns all rows from table
    Optional<List<User>> getAll();

    //Returns true if one (or more) exists in the table with specified email,
    //otherwise returns false
    boolean isEmailExists(String email);

    //Returns true if specified email exists in the table for any other except specified userId,
    //otherwise returns false
    boolean isOtherUsersWithEmailExist(Long userId, String email);

    Optional<User> getUserByEmail(String email);

    //Returns count of rows in the table
    Integer getCount();

    //Returns count of page with specified rows per page, starting from the offset position
    Optional<List<User>> getPages(int rowsPerPage, int offset);
}
