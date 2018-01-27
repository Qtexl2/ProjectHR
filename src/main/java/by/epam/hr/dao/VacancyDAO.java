package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Vacancy;

import java.util.List;

public interface VacancyDAO extends BaseDAO<Long, Vacancy> {
    List<Vacancy> selectAll() throws DAOException;
    boolean changeStatus(Long id) throws DAOException;
    List<Vacancy> selectActualVacancy() throws DAOException;
    List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException;
    boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws DAOException;
}
