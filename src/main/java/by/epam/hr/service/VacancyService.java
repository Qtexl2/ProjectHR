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

/**
 * The Class VacancyService.
 */
public class VacancyService {

    /** The vacancy DAO. */
    private VacancyDAO vacancyDAO;

    /**
     * Instantiates a new vacancy service.
     *
     * @throws ServiceException the service exception
     */
    public VacancyService() throws ServiceException {
        try {
            vacancyDAO = new VacancyMysqlDAO(false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select vacancy by loc and title.
     *
     * @param job the job
     * @param location the location
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLocAndTitle(job,location);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Insert vacancy.
     *
     * @param vacancy the vacancy
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertVacancy (Vacancy vacancy) throws ServiceException {
        try{
            return vacancyDAO.insert(vacancy);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update vacancy.
     *
     * @param vacancy the vacancy
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean updateVacancy (Vacancy vacancy) throws ServiceException {
        try{
            return vacancyDAO.update(vacancy);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete vacancy.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean deleteVacancy (Long id) throws ServiceException {
        try{
            return vacancyDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select vacancy by employer.
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Vacancy> selectVacancyByEmployer(Long id) throws ServiceException {
        try{
            return vacancyDAO.selectVacancyByLEmployer(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select vacancy by id.
     *
     * @param id the id
     * @return the vacancy
     * @throws ServiceException the service exception
     */
    public Vacancy selectVacancyById(Long id) throws ServiceException {
        try{
            return vacancyDAO.selectByID(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Insert vacancy and profile.
     *
     * @param idProfile the id profile
     * @param idVacancy the id vacancy
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws ServiceException {
        boolean status = true;
        TransactionManager transactionManager = null;

        try{
            transactionManager = new TransactionManager();
            VacancyDAO vacancyDAO = new VacancyMysqlDAO(true);
            MessageDAO messageDAO = new MessageMysqlDAO(true);
            transactionManager.begin(vacancyDAO,messageDAO);
            String mes= "<a href='/controller?command=page&id="+idProfile+"'>Кандидат</a> откликнулся на  " +
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
