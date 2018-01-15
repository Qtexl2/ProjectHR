package by.hr.dao;

import by.hr.entity.Vacancy;
import by.hr.exception.DAOException;

import java.util.List;

public interface VacancyDAO {
    List<Vacancy> selectAll() throws DAOException;
    boolean changeStatus(Long id) throws DAOException;
    List<Vacancy> selectActualVacancy() throws DAOException;
    List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException;
}
