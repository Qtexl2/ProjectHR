package by.epam.hr.dao;


import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.ConnectionPoolException;
import by.epam.hr.exception.DAOException;

import java.sql.SQLException;

/**
 * The Class TransactionManager.
 */
public class TransactionManager {

    /** The connection. */
    private PooledConnection connection;

    /**
     * Instantiates a new transaction manager.
     *
     * @throws DAOException the DAO exception
     */
    public TransactionManager() throws DAOException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception in constructor", e);
        }
    }

    /**
     * Begin.
     *
     * @param baseDAOs the base DAOs
     * @throws DAOException the DAO exception
     */
    public void begin(BaseDAO... baseDAOs) throws DAOException {
        try{
            connection.setAutoCommit(false);
            for (BaseDAO baseDao: baseDAOs) {
                baseDao.setConnection(connection);
            }
        }
        catch (SQLException e) {
            throw new DAOException("Exception in method Begin Transaction", e);
        }
    }

    /**
     * Commit.
     *
     * @throws DAOException the DAO exception
     */
    public void commit() throws DAOException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException("Exception in method Commit Transaction", e);
        }
    }

    /**
     * Rollback.
     *
     * @throws DAOException the DAO exception
     */
    public void rollback() throws DAOException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException("Exception in method Rollback Transaction", e);
        }
    }

    /**
     * Close connection.
     */
    public void closeConnection(){
        if (connection != null) {
            connection.close();
        }
    }

}
