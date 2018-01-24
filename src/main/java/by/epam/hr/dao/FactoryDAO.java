package by.epam.hr.dao;


import by.epam.hr.dao.dbmysql.FactoryMysqlDAO;

public interface FactoryDAO {
    //TODO СПРОСИТЬ ПРО ТАКУЮ РЕАЛИЗАЦИЮ
    static FactoryDAO getInstance(){
        return FactoryMysqlDAO.getInstance();
    }
    VacancyDAO getVacancyDAO();
    InterviewDAO getInterviewDAO();
    MessageDAO getMessageDAO();
    ProfileDAO getProfileDAO();
}
