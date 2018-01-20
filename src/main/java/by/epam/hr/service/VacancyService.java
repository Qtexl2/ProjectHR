package by.epam.hr.service;

import by.epam.hr.dao.VacancyDAO;
import by.epam.hr.dao.dbmysql.VacancyMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Vacancy;

import java.util.List;

public class VacancyService {
    public static VacancyDAO vacancyDAO = new VacancyMysqlDAO();

    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLocAndTitle(job,location);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
