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

            if(!vacancyDAO.insertVacancyAndProfile(idProfile, idVacancy)){
                status = false;
            }
            else {
                Long idReceiver = vacancyDAO.selectIdOwnerByIdVacancy(idVacancy);
                if(idProfile != null){
                    Message message = new Message("БЛА БЛА БЛА НА ПЕРВЫЙ РАЗ",idProfile, idReceiver);
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
