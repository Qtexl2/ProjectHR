package by.epam.hr.dao.dbmysql;

import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.DAOException;
import by.epam.hr.dao.MessageDAO;
import by.epam.hr.model.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class MessageMysqlDAO.
 */
public class MessageMysqlDAO extends MessageDAO{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(MessageMysqlDAO.class);

    /** The Constant SQL_INSERT_MESSAGE. */
    private static final String SQL_INSERT_MESSAGE = "INSERT INTO message " +
            "(message.message_text, message.message_time, message.profile_sender_id) " +
            "values (?, NOW(),?) ";

    /** The Constant SQL_INSERT_MESSAGE_. */
    private static final String SQL_INSERT_MESSAGE_ = "INSERT INTO message " +
            "(message.message_text, message.message_time, message.profile_sender_id) " +
            "values (?, NOW(),?) ";

    /** The Constant SQL_INSERT_MESSAGE_AND_USER. */
    private static final String SQL_INSERT_MESSAGE_AND_USER = "INSERT INTO message_and_user " +
            "(message_and_user.message_id, message_and_user.profile_reception_id) " +
            "values (last_insert_id(),?) ";

    /** The Constant SQL_SELECT_MESSAGE_BY_ID. */
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT m.message_id, m.message_text, m.message_time, " +
            "m.profile_sender_id, mu.profile_reception_id from message m " +
            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
            "WHERE m.message_id=? ";

    /** The Constant SQL_SELECT_DIALOG_BY_ID. */
    private static final String SQL_SELECT_DIALOG_BY_ID ="SELECT m.message_id, m.message_text, m.message_time, " +
            "m.profile_sender_id, mu.profile_reception_id from message m " +
            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
            "WHERE (m.profile_sender_id = ? and mu.profile_reception_id = ?) or (m.profile_sender_id = ? and mu.profile_reception_id = ?) " +
            "ORDER BY m.message_id ";

    /** The Constant SQL_UPDATE_MESSAGE_BY_ID. */
    private static final String SQL_UPDATE_MESSAGE_BY_ID = "UPDATE message m SET m.message_text=? WHERE m.message_id=? ";

    /** The Constant SQL_DELETE_MESSAGE_BY_ID. */
    private static final String SQL_DELETE_MESSAGE_BY_ID = "DELETE FROM message WHERE message.message_id=? ";

    /**
     * Instantiates a new message mysql DAO.
     *
     * @param isTransaction the is transaction
     * @throws DAOException the DAO exception
     */
    public MessageMysqlDAO(boolean isTransaction) throws DAOException {
        this.isTransaction = isTransaction;
    }

    /**
     * Instantiates a new message mysql DAO.
     *
     * @param connection the connection
     */
    public MessageMysqlDAO(PooledConnection connection) {
        this.connection = connection;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#setConnection(by.epam.hr.connection.PooledConnection)
     */
    public void setConnection(PooledConnection connection) {
        this.connection = connection;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#delete(java.lang.Object)
     */
    public boolean delete(Long id) throws DAOException {
        return deleteEntity(id,SQL_DELETE_MESSAGE_BY_ID);
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#selectByID(java.lang.Object)
     */
    @Override
    public Message selectByID(Long id) throws DAOException {
        Message message = null;
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_MESSAGE_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                message = init(result);
            }
            else {
                throw new DAOException("Exception in method selectByID. Message ID not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectByID: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return message;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#update(by.epam.hr.model.Entity)
     */
    @Override
    public boolean update(Message item) throws DAOException {
        checkInput(item);
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_MESSAGE_BY_ID);
            statement.setString(1,item.getMessageText());
            statement.setLong(2,item.getMessageId());
            statement.executeUpdate();
            status = true;

        } catch ( SQLException e) {
            throw new DAOException("Exception in method updateByID: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#insert(by.epam.hr.model.Entity)
     */
    @Override
    public boolean insert(Message item) throws DAOException{
        checkInput(item);
        checkTransaction();

        boolean status = false;
        PreparedStatement statementMessage = null;
        PreparedStatement statementMessageUser = null;
        try{
            connection.setAutoCommit(false);
            statementMessage = connection.prepareStatement(SQL_INSERT_MESSAGE);
            statementMessage.setString(1,item.getMessageText());
            statementMessage.setLong(2,item.getProfileSenderId());
            statementMessage.executeUpdate();
            statementMessageUser = connection.prepareStatement(SQL_INSERT_MESSAGE_AND_USER);
            statementMessageUser.setLong(1,item.getProfileReceptionId());
            statementMessageUser.executeUpdate();
            if(!isTransaction) {
                connection.commit();
            }
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method insert: ",e);
        } finally {
            try{
                if(statementMessageUser != null){
                    statementMessageUser.close();
                }
                if(statementMessage != null){
                    statementMessage.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"Statement can not be closed ",e);
            } finally {
                try {
                    if(!isTransaction) {
                        connection.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    connection.setLogicalClose(true);
                    LOGGER.log(Level.WARN,"Connection can not set autoCommit. It will be closed ",e);

                }
                finally {
                    closeConnection(connection);
                }
            }
        }
        return status;
    }

    /**
     * @see by.epam.hr.dao.MessageDAO#selectDialogById(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException{
        List<Message> messages = new ArrayList<>();
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_DIALOG_BY_ID);
            statement.setLong(1,idSender);
            statement.setLong(2,idReceiver);
            statement.setLong(3,idReceiver);
            statement.setLong(4,idSender);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Message message = init(result);
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectDialogById: ",e);
        }finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return messages;
    }

    /**
     * Inits the.
     *
     * @param resultSet the result set
     * @return the message
     * @throws SQLException the SQL exception
     */
    private Message init(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setMessageId(resultSet.getLong("message_id"));
        message.setMessageText(resultSet.getString("message_text"));
        message.setMessageTime(resultSet.getTimestamp("message_time"));
        message.setProfileReceptionId(resultSet.getLong("profile_reception_id"));
        message.setProfileSenderId(resultSet.getLong("profile_sender_id"));
        return message;
    }
}
