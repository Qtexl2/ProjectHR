package by.epam.hr.service;

import by.epam.hr.dao.MessageDAO;
import by.epam.hr.dao.TransactionManager;
import by.epam.hr.dao.VacancyDAO;
import by.epam.hr.dao.dbmysql.MessageMysqlDAO;
import by.epam.hr.dao.dbmysql.VacancyMysqlDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Message;
import by.epam.hr.model.Vacancy;

import java.util.List;

public class VacancyService {
    private VacancyDAO vacancyDAO;
    public VacancyService() throws ServiceException {
        try {
            vacancyDAO = new VacancyMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLocAndTitle(job,location);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public boolean insertVacancy (Vacancy vacancy) throws ServiceException {
        try{
            return vacancyDAO.insert(vacancy);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public List<Vacancy> selectVacancyByEmployer(Long id) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLEmployer(id);
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


    public boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws ServiceException {
        boolean status = true;
        TransactionManager transactionManager = null;
        try{
            transactionManager = new TransactionManager();
            VacancyDAO vacancyDAO = new VacancyMysqlDAO(true);
            MessageDAO messageDAO = new MessageMysqlDAO(true);
            transactionManager.begin(vacancyDAO,messageDAO);
            String mes= "<a href='/controller?command=page&id="+idProfile+"'>Кандидат</a> откликнулся на Вашу " +
                    "<a href='/controller?command=vacancy&vacancyId="+idVacancy+"'>вакансию</a>";

            if(!vacancyDAO.insertVacancyAndProfile(idProfile, idVacancy)){
                status = false;
            }
            else {
                Long idReceiver = vacancyDAO.selectIdOwnerByIdVacancy(idVacancy);
                if(idProfile != null){
                    Message message = new Message(mes,idProfile, idReceiver);
                    if(!messageDAO.insert(message)){
                        status = false;
                    }
                }
            }
            if(status){
                transactionManager.commit();
            }
            else  {
                throw new DAOException("Transaction failed");
            }
        } catch (DAOException e) {
            try {
                if(transactionManager != null){
                    transactionManager.rollback();
                }
                throw new ServiceException(e);

            } catch (DAOException ex) {
                throw new ServiceException(ex);
            }

        }
        finally {
            if(transactionManager != null){
                transactionManager.closeConnection();
            }
        }
        return status;
    }

}
