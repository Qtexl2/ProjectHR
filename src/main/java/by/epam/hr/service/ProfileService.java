package by.epam.hr.service;

import by.epam.hr.dao.ProfileDAO;
import by.epam.hr.dao.dbmysql.ProfileMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;

public class ProfileService {

    public static ProfileDAO profileDAO = new ProfileMysqlDAO();

    public boolean createNewProfile(Profile profile) throws ServiceException {
        try{
            return profileDAO.insert(profile);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Profile checkEmailAndPassword(String email, String password) throws ServiceException {
        try{
            return profileDAO.checkUser(email, password);

        }catch (DAOException e) {
            throw new ServiceException(e);
        }
    }




}
