package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Interview;

import java.util.List;

public abstract class InterviewDAO extends BaseDAO<Long,Interview> {

    public abstract List<Interview> selectAll() throws DAOException;
    public abstract List<Interview> selectInterviewByCandidateID(Long id) throws DAOException;
    public abstract List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException;
    public abstract List<Interview> selectInterviewByEmployerID(Long id) throws DAOException;
    public abstract List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException;
    public abstract List<Interview> selectActualInterview() throws DAOException;
}