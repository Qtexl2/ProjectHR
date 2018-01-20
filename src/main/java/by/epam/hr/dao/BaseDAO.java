package by.epam.hr.dao;

import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.ConnectionPoolException;
import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDAO<K,T extends Entity>{
    boolean insert(T item) throws DAOException;
    boolean update(T item) throws DAOException;
    boolean delete(K id) throws DAOException;
    T selectByID(K id) throws DAOException;

    default void closeStatement(PooledConnection connection, Statement statement) throws DAOException {
        try{
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method: ",e);//log
        } finally {
            connection.close();
        }
    }

    default void checkInput(T item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }
    }
    default boolean deleteQuery(Long id, String sql) throws DAOException {//entity
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method deleteQuery: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

}
