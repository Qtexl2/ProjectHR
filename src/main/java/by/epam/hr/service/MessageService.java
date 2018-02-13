package by.epam.hr.service;

import by.epam.hr.dao.MessageDAO;
import by.epam.hr.dao.dbmysql.MessageMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Message;

import java.util.List;

/**
 * The Class MessageService.
 */
public class MessageService {

    /** The message DAO. */
    private MessageDAO messageDAO;

    /**
     * Instantiates a new message service.
     *
     * @throws ServiceException the service exception
     */
    public MessageService() throws ServiceException {
        try {
            messageDAO = new MessageMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select dialog by id.
     *
     * @param idSender the id sender
     * @param idReceiver the id receiver
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Message> selectDialogById(Long idSender, Long idReceiver) throws ServiceException {
        List<Message> messages = null;
        try {
            if(idSender != null && idReceiver != null){
                messages = messageDAO.selectDialogById(idSender, idReceiver);
            }

        } catch (DAOException e) {
            throw new ServiceException("CreateNewProfile have a problem." ,e);
        }
        return messages;
    }

    /**
     * Insert message.
     *
     * @param idSender the id sender
     * @param idReceiver the id receiver
     * @param text the text
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertMessage(Long idSender,Long idReceiver, String text) throws ServiceException{
        try{
            Message message = new Message(text,idSender,idReceiver);
            return messageDAO.insert(message);
        }
        catch (DAOException e) {
            throw new ServiceException("CreateNewProfile have a problem." ,e);
        }
    }
}
