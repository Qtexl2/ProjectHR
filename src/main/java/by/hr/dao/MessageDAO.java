package by.hr.dao;

import by.hr.entity.Profile;
import by.hr.exception.DAOException;

import java.util.List;

public interface MessageDAO<Long, Message> {
    boolean insert(Message item) throws DAOException;
    boolean update(Message item) throws DAOException;
    boolean delete(Long id) throws DAOException;
    List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException;

}
