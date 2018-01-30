package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Message;

import java.util.List;

public abstract class MessageDAO extends BaseDAO<Long,Message> {
    public abstract List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException;

}
