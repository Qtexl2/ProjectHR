package by.hr.dao;

import by.hr.entity.Profile;
import by.hr.exception.DAOException;

import java.util.List;

public interface ProfileDAO{
    List<Profile> selectAll() throws DAOException;
    Profile checkUser(String email, String password) throws DAOException;
    List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException;

}
