package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Message;

import java.util.List;

/**
 * The Class MessageDAO.
 */
public abstract class MessageDAO extends BaseDAO<Long,Message> {

    /**
     * Select dialog by id.
     *
     * @param idSender the id sender
     * @param idReceiver the id receiver
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException;

}
