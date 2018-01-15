package by.hr.dao;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.entity.Entity;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAO<K,T extends Entity>{
    public abstract boolean insert(T item) throws DAOException;
    public abstract boolean update(T item) throws DAOException;
    public abstract boolean delete(K id) throws DAOException;
    public abstract T selectByID(K id) throws DAOException;

    protected void closeStatement(PooledConnection connection, Statement statement) throws DAOException {
        try{
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method: ",e);
        } finally {
            connection.close();
        }
    }

    protected void checkInput(T item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }
    }
    protected boolean deleteQuery(Long id, String sql) throws DAOException {
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
