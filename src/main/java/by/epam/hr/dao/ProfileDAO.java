package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Profile;

import java.util.List;

public interface ProfileDAO extends BaseDAO<Long, Profile> {
    List<Profile> selectAll() throws DAOException;
    Profile checkUser(String email, String password) throws DAOException;
    List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException;

}
