package by.hr.dao.dbmysql;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.dao.AbstractDAO;
import by.hr.dao.MessageDAO;
import by.hr.entity.Message;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageMysqlDAO extends AbstractDAO implements MessageDAO<Long, Message>{

    private static final String SQL_INSERT_MESSAGE = "INSERT INTO message " +
            "(message.message_text, message.message_time, message.profile_sender_id) " +
            "values (?, NOW(),?) ";
    private static final String SQL_INSERT_MESSAGE_AND_USER = "INSERT INTO message_and_user " +
            "(message_and_user.message_id, message_and_user.profile_reception_id) " +
            "values (last_insert_id(),?) ";

    private static final String SQL_SELECT_DIALOG_BY_ID ="SELECT m.message_id, m.message_text, m.message_time, " +
            "m.profile_sender_id, mu.profile_reception_id from message m " +
            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
            "WHERE (m.profile_sender_id = ? and mu.profile_reception_id = ?) or (m.profile_sender_id = ? and mu.profile_reception_id = ?) " +
            "ORDER BY m.message_id ";
    private static final String SQL_UPDATE_MESSAGE_BY_ID = "UPDATE message m SET m.message_text=? WHERE m.message_id=? ";
    private static final String SQL_DELETE_MESSAGE_BY_ID = "DELETE FROM message WHERE message.message_id=? ";


    public boolean delete(Long id) throws DAOException {
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_MESSAGE_BY_ID);
            statement.setLong(1,id);
            statement.executeUpdate();
            status = true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method delete: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }


    public boolean update(Message item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }

        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_MESSAGE_BY_ID);
            statement.setString(1,item.getMessageText());
            statement.setLong(2,item.getMessageID());
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method updateByID(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    @Override
    public boolean insert(Message item) throws DAOException{
        if( item == null){
            throw new DAOException("Input object is null");
        }

        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statementMessage = null;
        PreparedStatement statementMessageUser = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statementMessage = connection.prepareStatement(SQL_INSERT_MESSAGE);
            statementMessage.setString(1,item.getMessageText());
            statementMessage.setLong(2,item.getProfileSenderID());
            statementMessage.executeUpdate();
            statementMessageUser = connection.prepareStatement(SQL_INSERT_MESSAGE_AND_USER);
            statementMessageUser.setLong(1,item.getProfileReceptionID());
            statementMessageUser.executeUpdate();
            connection.commit();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
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
                throw new DAOException("Exception in method insert: ",e);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    connection.setLogicalClose(true);
                    throw new DAOException("Exception in method insert: ",e);
                }
                finally {
                    connection.close();
                }
            }
        }
        return status;
    }



    @Override
    public List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException{
        List<Message> messages = new ArrayList<>();
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_DIALOG_BY_ID);
            statement.setLong(1,idSender);
            statement.setLong(2,idReceiver);
            statement.setLong(3,idReceiver);
            statement.setLong(4,idSender);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Message message = new Message();
                message.setMessageID(result.getLong("message_id"));
                message.setMessageText(result.getString("message_text"));
                message.setMessageTime(result.getTimestamp("message_time"));
                message.setProfileReceptionID(result.getLong("profile_reception_id"));
                message.setProfileSenderID(result.getLong("profile_sender_id"));
                messages.add(message);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectDialogById: ",e);
        }finally {
            closeStatement(connection,statement);
        }
        return messages;
    }

    public static void main(String[] args) throws DAOException, InterruptedException {
        MessageMysqlDAO messageMysqlDAO = new MessageMysqlDAO();
//        Message message = new Message();
//        message.setMessageText("НОВАЯ РАБОТА ЭТО КРУТО");
//        message.setProfileSenderID(1L);
//        message.setProfileReceptionID(10L);
//        messageMysqlDAO.insert(message);
//        System.out.println(messageMysqlDAO.selectDialogById(1L,10L));
//        message.setMessageID(25L);
//        message.setMessageText("Но не спейлфорс )");
//        System.out.println(messageMysqlDAO.update(message));
//        System.out.println(messageMysqlDAO.selectDialogById(1L,10L));

//        messageMysqlDAO.delete()
//        messageMysqlDAO.insert(message);
        messageMysqlDAO.delete(26L);
        messageMysqlDAO.delete(25L);
        messageMysqlDAO.delete(24L);
        messageMysqlDAO.delete(16L);
//        messageMysqlDAO.delete(21L);
//        messageMysqlDAO.delete(22L);
//        messageMysqlDAO.delete(23L);
        ConnectionPool.getInstance().destroy();

    }
}
