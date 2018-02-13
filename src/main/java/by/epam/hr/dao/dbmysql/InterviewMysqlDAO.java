package by.epam.hr.dao.dbmysql;

import by.epam.hr.exception.DAOException;
import by.epam.hr.model.Interview;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.dao.InterviewDAO;
import by.epam.hr.model.InterviewType;
import by.epam.hr.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class InterviewMysqlDAO.
 */
public class InterviewMysqlDAO  extends InterviewDAO{

    /** The Constant SQL_SELECT_ALL_INTERVIEW. */
    private static final String SQL_SELECT_ALL_INTERVIEW = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview ";

    /** The Constant SQL_SELECT_ALL_INTERVIEW_CANDIDATE. */
    private static final String SQL_SELECT_ALL_INTERVIEW_CANDIDATE = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.candidate_id = ? ";

    /** The Constant SQL_SELECT_ALL_INTERVIEW_EMPLOYER. */
    private static final String SQL_SELECT_ALL_INTERVIEW_EMPLOYER = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.employer_id = ? ";

    /** The Constant SQL_SELECT_INTERVIEW_BY_ID. */
    private static final String SQL_SELECT_INTERVIEW_BY_ID = "SELECT  interview.interview_id,interview.interview_time, " +
            "interview.interview_description, interview.type_interview, interview.technical_specialist_id, " +
            "interview.candidate_id, interview.employer_id FROM interview WHERE interview.interview_id = ? ";

    /** The Constant SQL_UPDATE_INTERVIEW_BY_ID. */
    private static final String SQL_UPDATE_INTERVIEW_BY_ID = "UPDATE interview SET interview.interview_time=?, " +
            "interview.interview_description=?, interview.type_interview=?  WHERE interview.interview_id=?";

    /** The Constant SQL_DELETE_INTERVIEW_BY_ID. */
    private static final String SQL_DELETE_INTERVIEW_BY_ID = "DELETE FROM interview WHERE interview.interview_id=?";

    /** The Constant SQL_INSERT_INTERVIEW. */
    private static final String SQL_INSERT_INTERVIEW = "INSERT INTO interview " +
            "(interview.interview_time, interview.interview_description, interview.type_interview, " +
            "interview.candidate_id, interview.employer_id) VALUES(?,?,?,?,?)";

    /** The Constant ACTUAL_POSTFIX. */
    private static final String ACTUAL_POSTFIX = "interview.interview_time > now()";

    /** The Constant WHERE. */
    private static final String WHERE = "WHERE ";

    /** The Constant AND. */
    private static final String AND = "AND ";

    /**
     * Instantiates a new interview mysql DAO.
     *
     * @param connection the connection
     */
    public InterviewMysqlDAO(PooledConnection connection) {
        this.connection = connection;
    }

    /**
     * Instantiates a new interview mysql DAO.
     *
     * @param isTransaction the is transaction
     * @throws DAOException the DAO exception
     */
    public InterviewMysqlDAO(boolean isTransaction) throws DAOException {
        this.isTransaction = isTransaction;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#setConnection(by.epam.hr.connection.PooledConnection)
     */
    public void setConnection(PooledConnection connection) {
        this.connection = connection;
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectAll()
     */
    @Override
    public List<Interview> selectAll() throws DAOException {
        return selectInterview(SQL_SELECT_ALL_INTERVIEW);
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectInterviewByCandidateID(java.lang.Long)
     */
    @Override
    public List<Interview> selectInterviewByCandidateID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_CANDIDATE);
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectActualInterviewByCandidateID(java.lang.Long)
     */
    @Override
    public List<Interview> selectActualInterviewByCandidateID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_CANDIDATE + AND + ACTUAL_POSTFIX);
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectInterviewByEmployerID(java.lang.Long)
     */
    @Override
    public List<Interview> selectInterviewByEmployerID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_EMPLOYER);
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectActualInterviewByEmployerID(java.lang.Long)
     */
    @Override
    public List<Interview> selectActualInterviewByEmployerID(Long id) throws DAOException {
        return selectInterviewByID(id,SQL_SELECT_ALL_INTERVIEW_EMPLOYER + AND + ACTUAL_POSTFIX);
    }

    /**
     * @see by.epam.hr.dao.InterviewDAO#selectActualInterview()
     */
    @Override
    public List<Interview> selectActualInterview() throws DAOException {
        return selectInterview(SQL_SELECT_ALL_INTERVIEW + WHERE + ACTUAL_POSTFIX);
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#selectByID(java.lang.Object)
     */
    @Override
    public Interview selectByID(Long id) throws DAOException {
        checkTransaction();
        Interview interview = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_INTERVIEW_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                interview = initInterview(result);
            }
            else {
                throw new DAOException("Exception in method selectByID(). Interview ID not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectByID(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return interview;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#update(by.epam.hr.model.Entity)
     */
    @Override
    public boolean update(Interview item) throws DAOException {
        checkInput(item);
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_INTERVIEW_BY_ID);
            statement.setTimestamp(1,item.getInterviewTime());
            statement.setString(2,item.getInterviewDescription());
            statement.setString(3,item.getInterviewType().name().toLowerCase());
            statement.setLong(4,item.getInterviewId());
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method updateByID(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Long id) throws DAOException {
        return deleteEntity(id,SQL_DELETE_INTERVIEW_BY_ID);

    }

    /**
     * Select interview.
     *
     * @param sql the sql
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Interview> selectInterview(String sql) throws DAOException {
        List<Interview> interviews = new ArrayList<>();
        checkTransaction();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Interview interview;
                interview = initInterview(result);
                interviews.add(interview);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectInterview(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return interviews;
    }

    /**
     * Select interview by ID.
     *
     * @param id the id
     * @param sql the sql
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Interview> selectInterviewByID(Long id,String sql) throws DAOException {
        List<Interview> interviews = new ArrayList<>();
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Interview interview;
                interview = initInterview(result);
                interviews.add(interview);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectInterviewByCandidateID(): ",e);
        }finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return interviews;
    }

    /**
     * Inits the interview.
     *
     * @param resultSet the result set
     * @return the interview
     * @throws SQLException the SQL exception
     */
    private Interview initInterview(ResultSet resultSet) throws SQLException {
        Interview interview = new Interview();
        interview.setInterviewId(resultSet.getLong("interview_id"));
        interview.setInterviewTime(resultSet.getTimestamp("interview_time"));
        interview.setInterviewDescription(resultSet.getString("interview_description"));
        interview.setInterviewType(InterviewType.valueOf(resultSet.getString("type_interview").toUpperCase()));
        interview.setTechnicalId(resultSet.getLong("technical_specialist_id"));
        interview.setCandidateId(resultSet.getLong("candidate_id"));
        interview.setEmployerId(resultSet.getLong("employer_id"));
        return interview;
    }

    /**
     * @see by.epam.hr.dao.BaseDAO#insert(by.epam.hr.model.Entity)
     */
    @Override
    public boolean insert(Interview item) throws DAOException {
        checkInput(item);
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_INSERT_INTERVIEW);
            statement.setTimestamp(1,item.getInterviewTime());
            statement.setString(2,item.getInterviewDescription());
            statement.setString(3,item.getInterviewType().name().toLowerCase());
            statement.setLong(4,item.getCandidateId());
            statement.setLong(5,item.getEmployerId());
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method insert: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }
}
