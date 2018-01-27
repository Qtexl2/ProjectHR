package by.epam.hr.service;

import by.epam.hr.dao.FactoryDAO;
import by.epam.hr.dao.ProfileDAO;
import by.epam.hr.dao.dbmysql.ProfileMysqlDAO;
import by.epam.hr.encryption.EncryptionPassword;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;

import java.io.InputStream;

public class ProfileService {

    public static ProfileDAO profileDAO = FactoryDAO.getInstance().getProfileDAO();

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


}
