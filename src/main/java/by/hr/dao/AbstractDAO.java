package by.hr.dao;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAO{
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

    public boolean deleteQuery(Long id, String sql) throws DAOException {
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
            throw new DAOException("Exception in method deleteByID(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

}
