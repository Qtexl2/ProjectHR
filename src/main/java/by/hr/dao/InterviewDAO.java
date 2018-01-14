package by.hr.dao;

import by.hr.entity.Entity;
import by.hr.entity.Interview;
import by.hr.entity.InterviewType;
import by.hr.exception.DAOException;

import java.util.List;

public interface InterviewDAO<K,T extends Entity> {
    boolean deleteByID(K id) throws DAOException;

    boolean insert(long time, String description, InterviewType type, long technicalID, long candidateID, long employerID) throws DAOException;

    boolean updateByID(K id, long time, String description, InterviewType type, long technicalID) throws DAOException;

    List<T> selectAll() throws DAOException;

    List<T> selectInterviewByCandidateID(K id) throws DAOException;

    List<T> selectActualInterviewByCandidateID(K id) throws DAOException;

    List<T> selectInterviewByEmployerID(K id) throws DAOException;

    List<T> selectActualInterviewByEmployerID(K id) throws DAOException;

    List<T> selectActualInterview() throws DAOException;

    T selectByID(K id) throws DAOException;
}