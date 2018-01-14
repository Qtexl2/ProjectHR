package by.hr.dao.dbmysql;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.dao.AbstractDAO;
import by.hr.dao.InterviewDAO;
import by.hr.entity.Interview;
import by.hr.entity.InterviewType;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterviewMysqlDAO extends AbstractDAO implements InterviewDAO<Long,Interview>{
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String SQL_SELECT_ALL_INTERVIEW = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview ";

    private static final String SQL_SELECT_ALL_INTERVIEW_CANDIDATE = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.candidate_id = ? ";

    private static final String SQL_SELECT_ALL_INTERVIEW_EMPLOYER = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.employer_id = ? ";

    private static final String SQL_SELECT_INTERVIEW_BY_ID = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.interview_id = ? ";

    private static final String SQL_UPDATE_INTERVIEW_BY_ID = "UPDATE interview SET interview.interview_time=?, " +
            "interview.interview_description=?, interview.type_interview=?, interview.technical_specialist_id=? " +
            "WHERE interview.interview_id=?";

    private static final String SQL_DELETE_INTERVIEW_BY_ID = "DELETE FROM interview WHERE interview.interview_id=?";

    private static final String SQL_INSERT_INTERVIEW = "INSERT INTO interview " +
            "(interview.interview_time, interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id) VALUES(?,?,?,?,?,?)";

    private static final String ACTUAL_POSTFIX = "interview.interview_time > now()";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    @Override
    public List<Interview> selectAll() throws DAOException {
        return selectInterview(SQL_SELECT_ALL_INTERVIEW);
    }
    @Override
    public List<Interview> selectInterviewByCandidateID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_CANDIDATE);
    }
    @Override
    public List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_CANDIDATE + AND + ACTUAL_POSTFIX);
    }
    @Override
    public List<Interview> selectInterviewByEmployerID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_EMPLOYER);
    }
    @Override
    public List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_EMPLOYER + AND + ACTUAL_POSTFIX);
    }
    @Override
    public List<Interview> selectActualInterview() throws DAOException {
        return selectInterview(SQL_SELECT_ALL_INTERVIEW + WHERE + ACTUAL_POSTFIX);
    }
    @Override
    public Interview selectByID(Long id) throws DAOException {
        Interview interview = null;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_INTERVIEW_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                interview = initInterview(result);
            }
            else {
                throw new DAOException("Exception in method selectByID(). Interview ID not found");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectByID(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return interview;
    }
    @Override
    public boolean updateByID(Long id, long time, String description, InterviewType type, long technicalID) throws DAOException {
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_INTERVIEW_BY_ID);
            statement.setTimestamp(1,new Timestamp(time));
            statement.setString(2,description);
            statement.setString(3,type.name().toLowerCase());
            statement.setLong(4,technicalID);
            statement.setLong(5,id);
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method updateByID(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    @Override
    public boolean insert(long time, String description, InterviewType type, long technicalID, long candidateID, long employerID) throws DAOException {
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_INTERVIEW);
            statement.setTimestamp(1,new Timestamp(time));
            statement.setString(2,description);
            statement.setString(3,type.name().toLowerCase());
            statement.setLong(4,technicalID);
            statement.setLong(5,candidateID);
            statement.setLong(6,employerID);
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method insert(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    @Override
    public boolean deleteByID(Long id) throws DAOException {
        return deleteQuery(id,SQL_DELETE_INTERVIEW_BY_ID);
    }

    private List<Interview> selectInterview(String sql) throws DAOException {
        List<Interview> interviews = new ArrayList<>();
        PooledConnection connection = null;
        Statement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Interview interview;
                interview = initInterview(result);
                interviews.add(interview);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectInterview(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return interviews;
    }



    private List<Interview> selectInterviewByID(Long id,String sql) throws DAOException {
        List<Interview> interviews = new ArrayList<>();
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Interview interview;
                interview = initInterview(result);
                interviews.add(interview);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectInterviewByCandidateID(): ",e);
        }finally {
            closeStatement(connection,statement);
        }
        return interviews;
    }
    private Interview initInterview(ResultSet resultSet) throws SQLException {
        Interview interview = new Interview();
        interview.setInterviewID(resultSet.getLong("interview_id"));
        interview.setInterviewTime(resultSet.getTimestamp("interview_time"));
        interview.setInterviewDescription(resultSet.getString("interview_description"));
        interview.setInterviewType(InterviewType.valueOf(resultSet.getString("type_interview").toUpperCase()));
        interview.setTechnicalID(resultSet.getLong("technical_specialist_id"));
        interview.setCandidateID(resultSet.getLong("candidate_id"));
        interview.setEmployerID(resultSet.getLong("employer_id"));
        return interview;
    }

    public static void main(String[] args) throws DAOException, ConnectionPoolException, SQLException {
        InterviewMysqlDAO mysqlDAO = new InterviewMysqlDAO();
        System.out.println(mysqlDAO.selectAll());
        System.out.println(mysqlDAO.selectActualInterview());
        System.out.println(mysqlDAO.selectInterviewByCandidateID(1L));
        System.out.println(mysqlDAO.selectActualInterviewByCandidateID(1L));
        System.out.println("---------------");
        System.out.println(mysqlDAO.selectInterviewByEmployerID(8L));
        System.out.println(mysqlDAO.selectActualInterviewByEmployerID(8L));
        System.out.println("++++++++++++++");
        System.out.println(mysqlDAO.selectByID(1L));
        System.out.println("@@@@@@@@@@@@@@@@@");
//        System.out.println(mysqlDAO.updateByID(4,System.currentTimeMillis(),"HR не HR",InterviewType.TECHNICAL,1));
//        mysqlDAO.insertByID(System.currentTimeMillis(),"Вставлен 2",InterviewType.TECHNICAL,1,5,9);
//        mysqlDAO.deleteByID(8L);
        ConnectionPool.getInstance().destroy();

    }

}
