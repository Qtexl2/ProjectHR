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

/**
 * The Class BaseDAO.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public abstract class BaseDAO<K,T extends Entity>{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

    /**
     * Insert.
     *
     * @param item the item
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean insert(T item) throws DAOException;

    /**
     * Update.
     *
     * @param item the item
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean update(T item) throws DAOException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean delete(K id) throws DAOException;

    /**
     * Select by ID.
     *
     * @param id the id
     * @return the t
     * @throws DAOException the DAO exception
     */
    public abstract T selectByID(K id) throws DAOException;

    /** The is transaction. */
    protected boolean isTransaction = false;

    /** The connection. */
    protected PooledConnection connection;

    /**
     * Close statement.
     *
     * @param statement the statement
     */
    protected void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN,"Statement not closed",e);
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    protected void closeConnection(PooledConnection connection){
        if(!isTransaction) {
            if (connection != null) {
                connection.close();
            }
        }
    }


    /**
     * Check input.
     *
     * @param item the item
     * @throws DAOException the DAO exception
     */
    protected void checkInput(T item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }
    }

    /**
     * Check transaction.
     *
     * @throws DAOException the DAO exception
     */
    protected void checkTransaction() throws DAOException {
        if(!isTransaction){
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (ConnectionPoolException e) {
                throw new DAOException("Problem with a Connection Pool");
            }
        }
    }

    /**
     * Delete entity.
     *
     * @param id the id
     * @param sql the sql
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
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

    /**
     * Sets the connection.
     *
     * @param connection the new connection
     */
    protected void setConnection(PooledConnection connection){
        this.connection = connection;
    }
}
