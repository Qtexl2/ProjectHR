package by.hr.dao;

import by.hr.model.Interview;
import by.hr.exception.DAOException;

import java.util.List;

public interface InterviewDAO{

    List<Interview> selectAll() throws DAOException;
    List<Interview> selectInterviewByCandidateID(Long id) throws DAOException;
    List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException;
    List<Interview> selectInterviewByEmployerID(Long id) throws DAOException;
    List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException;
    List<Interview> selectActualInterview() throws DAOException;
}