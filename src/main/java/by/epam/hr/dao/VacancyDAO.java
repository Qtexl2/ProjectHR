package by.epam.hr.dao;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Vacancy;

import java.util.List;

public abstract class VacancyDAO extends BaseDAO<Long, Vacancy> {
    public abstract List<Vacancy> selectAll() throws DAOException;
    public abstract boolean changeStatus(Long id) throws DAOException;
    public abstract List<Vacancy> selectActualVacancy() throws DAOException;
    public abstract List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException;
    public abstract boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws DAOException;
    public abstract Long selectIdOwnerByIdVacancy(Long idVacancy) throws DAOException;
    public abstract List<Vacancy> selectVacancyByLEmployer(Long id) throws DAOException;
}
