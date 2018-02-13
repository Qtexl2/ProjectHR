package by.epam.hr.dao;

import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.ConnectionPoolException;
import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO<K,T extends Entity>{
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);
    public abstract boolean insert(T item) throws DAOException;
    public abstract boolean update(T item) throws DAOException;
    public abstract boolean delete(K id) throws DAOException;
    public abstract T selectByID(K id) throws DAOException;
    protected boolean isTransaction = false;
    protected PooledConnection connection;
    protected void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN,"Statement not closed",e);
        }
    }
    protected void closeConnection(PooledConnection connection){
        if(!isTransaction) {
            if (connection != null) {
                connection.close();
            }
        }
    }


    protected void checkInput(T item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }
    }
    protected void checkTransaction() throws DAOException {
        if(!isTransaction){
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (ConnectionPoolException e) {
                throw new DAOException("Problem with a Connection Pool");
            }
        }
    }
    protected boolean deleteEntity(Long id, String sql) throws DAOException {
        boolean status = false;
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method deleteEntity: ",e);
        } finally {
                closeStatement(statement);
                closeConnection(connection);
        }
        return status;
    }
    protected void setConnection(PooledConnection connection){
        this.connection = connection;
    }
}
