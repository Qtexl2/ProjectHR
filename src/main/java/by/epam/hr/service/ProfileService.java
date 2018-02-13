package by.epam.hr.service;

import by.epam.hr.dao.ProfileDAO;
import by.epam.hr.dao.dbmysql.ProfileMysqlDAO;
import by.epam.hr.encryption.EncryptionPassword;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.EnglishLevel;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;

import java.io.InputStream;
import java.util.List;

public class ProfileService {
    private ProfileDAO profileDAO;

    public ProfileService() throws ServiceException {
        try {
            profileDAO = new ProfileMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public boolean createNewProfile(Profile profile) throws ServiceException {
        boolean status;
        try {
            if(profileDAO.checkFreeEmail(profile.getEmail())){
                String password = EncryptionPassword.encrypt(profile.getPassword());
                profile.setPassword(password);
                profileDAO.insert(profile);
                status = true;
            }
            else{
                status = false;
            }
        } catch (DAOException e) {
            throw new ServiceException("CreateNewProfile have a problem." ,e);
        }
        return status;
    }

    public Profile checkEmailAndPassword(String email, String password) throws ServiceException {
        try{
            return profileDAO.checkUser(email, password);

        }catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Profile selectUserById(Long id) throws ServiceException {
        try{
            return profileDAO.selectByID(id);

        }catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updatePhoto(Long id, InputStream photo) throws ServiceException {

        try {
            return profileDAO.updatePhoto(id,photo);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public byte[] selectPhoto(Long id) throws ServiceException {

        try {
            return profileDAO.selectPhoto(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateProfile(Profile profile) throws ServiceException {
        try {
            return profileDAO.update(profile);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateBaseProfile(Profile profile) throws ServiceException {
        try {
            return profileDAO.updateBaseProfile(profile);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public boolean updateAfterInterview(Long id, String levelEn, String preInterview, String techInterview) throws ServiceException {
        try {
            return profileDAO.updateAfterInterview(EnglishLevel.valueOf(levelEn),preInterview,techInterview,id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateUser(String email, String password, Role role) throws ServiceException {
        boolean status;
        try {
            String pass = EncryptionPassword.encrypt(password);
            status = profileDAO.updateUser(email, pass, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return status;
    }


    public List<Profile> selectDialogById(Long id) throws ServiceException{

        try {
             return profileDAO.selectProfileHaveDialog(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public List<Profile> select() throws ServiceException{
        try {
            return profileDAO.selectAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean delete(Long id) throws ServiceException{
        try {
            return profileDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
