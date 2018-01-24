package by.epam.hr.dao.dbmysql;

import by.epam.hr.dao.*;

public class FactoryMysqlDAO implements FactoryDAO{
    private static final FactoryDAO instance = new FactoryMysqlDAO();
    private VacancyDAO vacancyDAO;
    private InterviewDAO interviewDAO;
    private MessageDAO messageDAO;
    private ProfileDAO profileDAO;

    private FactoryMysqlDAO(){
        vacancyDAO = new VacancyMysqlDAO();
        interviewDAO = new InterviewMysqlDAO();
        messageDAO = new MessageMysqlDAO();
        profileDAO = new ProfileMysqlDAO();

    }
    public static FactoryDAO getInstance(){
        return instance ;
    }

    public VacancyDAO getVacancyDAO() {
        return vacancyDAO;
    }

    public InterviewDAO getInterviewDAO() {
        return interviewDAO;
    }

    public MessageDAO getMessageDAO() {
        return messageDAO;
    }

    public ProfileDAO getProfileDAO() {
        return profileDAO;
    }
}
