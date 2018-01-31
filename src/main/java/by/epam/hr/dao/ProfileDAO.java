package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.EnglishLevel;
import by.epam.hr.model.Profile;

import java.io.InputStream;
import java.util.List;

public abstract class ProfileDAO extends BaseDAO<Long, Profile> {
    public abstract List<Profile> selectAll() throws DAOException;
    public abstract Profile checkUser(String email, String password) throws DAOException;
    public abstract List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException;
    public abstract boolean checkFreeEmail(String email) throws DAOException;
    public abstract byte[] selectPhoto(Long id) throws DAOException;
    public abstract boolean updateBaseProfile(Profile item) throws DAOException;
    public abstract boolean updatePhoto(Long id, InputStream photo) throws DAOException;
    public abstract boolean updateAfterInterview(EnglishLevel englishLevel, String preInterview, String techInterview, Long id)
            throws DAOException;
}
