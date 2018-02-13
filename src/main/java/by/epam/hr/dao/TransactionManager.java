package by.epam.hr.dao;


import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.ConnectionPoolException;
import by.epam.hr.exception.DAOException;

import java.sql.SQLException;

public class TransactionManager {
    private PooledConnection connection;

    public TransactionManager() throws DAOException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception in constructor", e);
        }
    }
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

    public void commit() throws DAOException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException("Exception in method Commit Transaction", e);
        }
    }

    public void rollback() throws DAOException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException("Exception in method Rollback Transaction", e);
        }
    }
    public void closeConnection(){
        if (connection != null) {
            connection.close();
        }
    }

}
