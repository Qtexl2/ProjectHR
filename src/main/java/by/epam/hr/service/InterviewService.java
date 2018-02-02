package by.epam.hr.service;

import by.epam.hr.dao.InterviewDAO;
import by.epam.hr.dao.dbmysql.InterviewMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Interview;

import java.util.List;

public class InterviewService {
    private InterviewDAO interviewDAO;

    public InterviewService() throws ServiceException {
        try {
            interviewDAO = new InterviewMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean insertInterview(Interview interview) throws ServiceException{
        try{
            return interviewDAO.insert(interview);
        }
        catch (DAOException e) {
            throw new ServiceException("insertInterview have a problem in service layer." ,e);
        }
    }

    public List<Interview> selectInterviewByCandidateId(Long id) throws ServiceException{
        try{
            return interviewDAO.selectActualInterviewByCandidateID(id);
        }
        catch (DAOException e) {
            throw new ServiceException("selectInterviewByCandidateId have a problem in service layer." ,e);
        }
    }

    public List<Interview> selectInterviewByEmployerID(Long id) throws ServiceException{
        try{
            return interviewDAO.selectInterviewByEmployerID(id);
        }
        catch (DAOException e) {
            throw new ServiceException("selectInterviewByEmployerID have a problem in service layer." ,e);
        }
    }
}
