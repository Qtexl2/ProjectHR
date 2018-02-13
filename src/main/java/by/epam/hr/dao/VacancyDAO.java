package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Vacancy;

import java.util.List;

/**
 * The Class VacancyDAO.
 */
public abstract class VacancyDAO extends BaseDAO<Long, Vacancy> {

    /**
     * Select all.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Vacancy> selectAll() throws DAOException;

    /**
     * Change status.
     *
     * @param id the id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean changeStatus(Long id) throws DAOException;

    /**
     * Select actual vacancy.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Vacancy> selectActualVacancy() throws DAOException;

    /**
     * Select vacancy by loc and title.
     *
     * @param job the job
     * @param location the location
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException;

    /**
     * Insert vacancy and profile.
     *
     * @param idProfile the id profile
     * @param idVacancy the id vacancy
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws DAOException;

    /**
     * Select id owner by id vacancy.
     *
     * @param idVacancy the id vacancy
     * @return the long
     * @throws DAOException the DAO exception
     */
    public abstract Long selectIdOwnerByIdVacancy(Long idVacancy) throws DAOException;

    /**
     * Select vacancy by L employer.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Vacancy> selectVacancyByLEmployer(Long id) throws DAOException;
}
