package by.epam.hr.service;

import by.epam.hr.dao.InterviewDAO;
import by.epam.hr.dao.dbmysql.InterviewMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Interview;

import java.util.List;

/**
 * The Class InterviewService.
 */
public class InterviewService {

    /** The interview DAO. */
    private InterviewDAO interviewDAO;

    /**
     * Instantiates a new interview service.
     *
     * @throws ServiceException the service exception
     */
    public InterviewService() throws ServiceException {
        try {
            interviewDAO = new InterviewMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Insert interview.
     *
     * @param interview the interview
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertInterview(Interview interview) throws ServiceException{
        try{
            return interviewDAO.insert(interview);
        }
        catch (DAOException e) {
            throw new ServiceException("insertInterview have a problem in service layer." ,e);
        }
    }

    /**
     * Select interview by candidate id.
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Interview> selectInterviewByCandidateId(Long id) throws ServiceException{
        try{
            return interviewDAO.selectActualInterviewByCandidateID(id);
        }
        catch (DAOException e) {
            throw new ServiceException("selectInterviewByCandidateId have a problem in service layer." ,e);
        }
    }

    /**
     * Select interview by employer ID.
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Interview> selectInterviewByEmployerID(Long id) throws ServiceException{
        try{
            return interviewDAO.selectInterviewByEmployerID(id);
        }
        catch (DAOException e) {
            throw new ServiceException("selectInterviewByEmployerID have a problem in service layer." ,e);
        }
    }

    /**
     * Delete interview.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean deleteInterview(Long id) throws ServiceException{
        try{
            return interviewDAO.delete(id);
        }
        catch (DAOException e) {
            throw new ServiceException("deleteInterview have a problem in service layer." ,e);
        }
    }

    /**
     * Update interview.
     *
     * @param interview the interview
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean updateInterview(Interview interview) throws ServiceException{
        try{
            return interviewDAO.update(interview);
        }
        catch (DAOException e) {
            throw new ServiceException("deleteInterview have a problem in service layer." ,e);
        }
    }

    /**
     * Select interview.
     *
     * @param id the id
     * @return the interview
     * @throws ServiceException the service exception
     */
    public Interview selectInterview(Long id) throws ServiceException{
        try{
            return interviewDAO.selectByID(id);
        }
        catch (DAOException e) {
            throw new ServiceException("selectInterview have a problem in service layer." ,e);
        }
    }
}
