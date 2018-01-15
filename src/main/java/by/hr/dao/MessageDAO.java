package by.hr.dao;

import by.hr.entity.Message;
import by.hr.entity.Profile;
import by.hr.exception.DAOException;

import java.util.List;

public interface MessageDAO {
    List<Message> selectDialogById(Long idSender, Long idReceiver) throws DAOException;

}
