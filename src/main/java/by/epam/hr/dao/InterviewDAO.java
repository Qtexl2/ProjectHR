package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Interview;

import java.util.List;

/**
 * The Class InterviewDAO.
 */
public abstract class InterviewDAO extends BaseDAO<Long,Interview> {

    /**
     * Select all.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectAll() throws DAOException;

    /**
     * Select interview by candidate ID.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectInterviewByCandidateID(Long id) throws DAOException;

    /**
     * Select actual interview by candidate ID.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException;

    /**
     * Select interview by employer ID.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectInterviewByEmployerID(Long id) throws DAOException;

    /**
     * Select actual interview by employer ID.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException;

    /**
     * Select actual interview.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Interview> selectActualInterview() throws DAOException;

    }
