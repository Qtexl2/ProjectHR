
package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.EnglishLevel;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;

import java.io.InputStream;
import java.util.List;

/**
 * The Class ProfileDAO.
 */
public abstract class ProfileDAO extends BaseDAO<Long, Profile> {

    /**
     * Select all.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Profile> selectAll() throws DAOException;

    /**
     * Check user.
     *
     * @param email the email
     * @param password the password
     * @return the profile
     * @throws DAOException the DAO exception
     */
    public abstract Profile checkUser(String email, String password) throws DAOException;

    /**
     * Select profile have dialog.
     *
     * @param idReceiver the id receiver
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException;

    /**
     * Check free email.
     *
     * @param email the email
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean checkFreeEmail(String email) throws DAOException;

    /**
     * Select photo.
     *
     * @param id the id
     * @return the byte[]
     * @throws DAOException the DAO exception
     */
    public abstract byte[] selectPhoto(Long id) throws DAOException;

    /**
     * Update base profile.
     *
     * @param item the item
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean updateBaseProfile(Profile item) throws DAOException;

    /**
     * Update photo.
     *
     * @param id the id
     * @param photo the photo
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean updatePhoto(Long id, InputStream photo) throws DAOException;

    /**
     * Update after interview.
     *
     * @param englishLevel the english level
     * @param preInterview the pre interview
     * @param techInterview the tech interview
     * @param id the id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean updateAfterInterview(EnglishLevel englishLevel, String preInterview, String techInterview, Long id)
            throws DAOException;

    /**
     * Update user.
     *
     * @param email the email
     * @param password the password
     * @param role the role
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean updateUser(String email, String password, Role role) throws DAOException;
}
