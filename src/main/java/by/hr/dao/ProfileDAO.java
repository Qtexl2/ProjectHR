package by.hr.dao;

import by.hr.entity.Profile;
import by.hr.exception.DAOException;

import java.util.List;

public interface ProfileDAO{
    boolean insert(Profile item) throws DAOException;
    boolean delete(Long id) throws DAOException;
    Profile selectByID(Long id) throws DAOException;
    List<Profile> selectAll() throws DAOException;
    boolean update(Profile item) throws DAOException;
    Profile authentication(String email, String password) throws DAOException;
    List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException;

}
