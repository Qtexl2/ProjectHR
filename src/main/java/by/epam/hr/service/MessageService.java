package by.epam.hr.service;

import by.epam.hr.dao.MessageDAO;
import by.epam.hr.dao.dbmysql.MessageMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Message;
import com.google.gson.Gson;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() throws ServiceException {
        try {
            messageDAO = new MessageMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
