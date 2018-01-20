package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Interview;

import java.util.List;

public interface InterviewDAO extends BaseDAO<Long,Interview> {

    List<Interview> selectAll() throws DAOException;
    List<Interview> selectInterviewByCandidateID(Long id) throws DAOException;
    List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException;
    List<Interview> selectInterviewByEmployerID(Long id) throws DAOException;
    List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException;
    List<Interview> selectActualInterview() throws DAOException;
}