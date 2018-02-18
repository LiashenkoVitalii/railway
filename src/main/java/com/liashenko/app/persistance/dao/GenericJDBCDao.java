package com.liashenko.app.persistance.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//The interface for generified DAO, contains methods for all base operations with db
public interface GenericJDBCDao<T extends Identified<PK>, PK extends Serializable> {

    //Returns sql query to check if PK exists in the table (isExists() method)
    String getExistsQuery();

    /*
     * Returns sql query to get all rows from the table (getAll() method)
     * SELECT * FROM [Table]
     */
    String getSelectQuery();

    /*
     * Returns sql query to insert new row to db (create() method)
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    String getCreateQuery();

    /*
     * Returns sql query to update existing row  (update() method)
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    String getUpdateQuery();

    /*
     * Returns sql query to delete existing row from table (delete() method)
     * DELETE FROM [Table] WHERE id= ?;
     */
    String getDeleteQuery();

    //If the row with PK exists returns true, otherwise - false
    boolean isExists(PK key);

    //Creates new row in the db corresponds to its object
    void create(T object);

    //Creates new row in the db corresponds to its object and returns it from db or empty optional
    Optional<T> persist(T object);

    //Returns an object corresponds to row with PK or empty optional
    Optional<T> getByPK(PK key);

    //Saves object's state to db
    void update(T object);

    //Deletes the row with PK corresponds to object's id
    void delete(T object);

    //Returns all rows from table
    Optional<List<T>> getAll();
}
