package by.epam.hr.service;

import by.epam.hr.dao.FactoryDAO;
import by.epam.hr.dao.VacancyDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Vacancy;

import java.util.List;

public class VacancyService {
    private static VacancyDAO vacancyDAO = FactoryDAO.getInstance().getVacancyDAO();

    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLocAndTitle(job,location);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Vacancy selectVacancyById(Long id) throws ServiceException {
        try{
            return vacancyDAO.selectByID(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


}
