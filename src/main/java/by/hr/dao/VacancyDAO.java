package by.hr.dao;

import by.hr.entity.Vacancy;
import by.hr.exception.DAOException;

import java.util.List;

public interface VacancyDAO {
    boolean deleteByID(Long id) throws DAOException;
    boolean updateByID(Long id, String title, String description,String location, Boolean status) throws DAOException;
    List<Vacancy> selectAll() throws DAOException;
    boolean insert(String title, String description, String location) throws DAOException;
    boolean changeStatus(Long id) throws DAOException;
    List<Vacancy> selectActualVacancy() throws DAOException;
    List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException;
    Vacancy selectByID(Long id) throws DAOException;
}
