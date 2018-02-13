package by.epam.hr.dao.dbmysql;

import by.epam.hr.dao.VacancyDAO;
import by.epam.hr.exception.DAOException;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.model.Vacancy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VacancyMysqlDAO extends VacancyDAO {

    private final static String SQL_DELETE_VACANCY_BY_ID = "DELETE FROM vacancy WHERE vacancy.vacancy_id=?";

    private static final String SQL_UPDATE_VACANCY_BY_ID = "UPDATE vacancy SET vacancy.vacancy_title=?, " +
            "vacancy.vacancy_description=?, vacancy.vacancy_location=?, vacancy.vacancy_status=?, vacancy.company=? WHERE vacancy.vacancy_id=?";

    private static final String SQL_UPDATE_STATUS_BY_ID = "UPDATE vacancy SET " +
            "vacancy.vacancy_status= not vacancy.vacancy_status  WHERE vacancy.vacancy_id=?";

    private static final String SQL_INSERT_VACANCY = "INSERT INTO vacancy " +
            "(vacancy.vacancy_title, vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status, " +
            "vacancy.company, vacancy.employer_id) VALUES(?,?,?,?,?,?)";

    private static final String SQL_INSERT_VACANCY_AND_PROFILE = "INSERT INTO profile_has_vacancy  " +
            "(profile_has_vacancy.profile_user_id, profile_has_vacancy.vacancy_vacancy_id) VALUES (?, ?) ";
    private static final String SQL_SELECT_ALL_VACANCY = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status, vacancy.company FROM vacancy ";

    private static final String SQL_SELECT_ACTUAL_VACANCY = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status, vacancy.company FROM vacancy WHERE vacancy.vacancy_status=1";

    private static final String SQL_SELECT_VACANCY_BY_ID = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status, vacancy.company FROM vacancy " +
            "WHERE vacancy.vacancy_id=?";

    private static final String SQL_SELECT_VACANCY_BY_EMPLOYER = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status, vacancy.company FROM vacancy " +
            "WHERE vacancy.employer_id=?";

    private static final String SQL_SELECT_EMPLOYER_BY_ID_VACANCY = "SELECT v.employer_id FROM vacancy v WHERE v.vacancy_id=? ";

    private static final String SQL_SELECT_BY_TITLE_AND_LOCAL_AND_DESC = "SELECT v.vacancy_id, v.vacancy_title, " +
            "v.vacancy_description, v.vacancy_location, v.company, v.vacancy_status FROM hr.vacancy v WHERE " +
            "(v.vacancy_title LIKE concat('%',replace(?,' ','%'),'%') " +
            "OR v.vacancy_description LIKE concat('%',replace(?,' ','%'),'%')) " +
            "AND v.vacancy_location LIKE concat('%',?,'%') " +
            "AND v.vacancy_status=true";

    public VacancyMysqlDAO(boolean isTransaction) throws DAOException {
        this.isTransaction = isTransaction;
    }
    public void setConnection(PooledConnection connection) {
        this.connection = connection;
    }

    public VacancyMysqlDAO(PooledConnection connection) {
        this.connection = connection;
    }

    private List<Vacancy> selectVacancy(String sql) throws DAOException {
        List<Vacancy> vacancies = new ArrayList<>();
        checkTransaction();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Vacancy vacancy;
                vacancy = initVacancy(result);
                vacancies.add(vacancy);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectVacancy(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return vacancies;
    }

    public  Long selectIdOwnerByIdVacancy(Long idVacancy) throws DAOException{
        Long idOwner;
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_EMPLOYER_BY_ID_VACANCY);
            statement.setLong(1,idVacancy);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                idOwner = result.getLong("employer_id");
            }
            else {
                throw new DAOException("Exception in method selectIdOwnerByIdVacancy. Vacancy ID not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectIdOwnerByIdVacancy: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return idOwner;
    }

    @Override
    public Vacancy selectByID(Long id) throws DAOException {
        Vacancy vacancy = null;
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_VACANCY_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                vacancy = initVacancy(result);
            }
            else {
                throw new DAOException("Exception in method selectByID(). Vacancy ID not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectByID(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException{
        List<Vacancy> vacancies = new ArrayList<>();
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_BY_TITLE_AND_LOCAL_AND_DESC);
            statement.setString(1,job);
            statement.setString(2,job);
            statement.setString(3,location);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Vacancy vacancy;
                vacancy = initVacancy(result);
                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            throw new DAOException("Exception in method selectVacancyByLocAndTitle(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return vacancies;
    }


    @Override
    public List<Vacancy> selectVacancyByLEmployer(Long id) throws DAOException{
        List<Vacancy> vacancies = new ArrayList<>();
        checkTransaction();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_VACANCY_BY_EMPLOYER);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Vacancy vacancy;
                vacancy = initVacancy(result);
                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            throw new DAOException("Exception in method selectVacancyByLEmployer(): ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return vacancies;
    }

    @Override
    public boolean insert(Vacancy item) throws DAOException{
        checkTransaction();
        checkInput(item);
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_INSERT_VACANCY);
            statement.setString(1,item.getVacancyTitle());
            statement.setString(2,item.getVacancyDescription());
            statement.setString(3,item.getLocation());
            statement.setBoolean(4,item.getVacancyStatus());
            statement.setString(5,item.getCompany());
            statement.setLong(6,item.getEmployerId());
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

    public boolean insertVacancyAndProfile(Long idProfile, Long idVacancy) throws DAOException{
        boolean status = false;
        PreparedStatement statement = null;
        checkTransaction();
        try{
            statement = connection.prepareStatement(SQL_INSERT_VACANCY_AND_PROFILE);
            statement.setLong(1, idProfile);
            statement.setLong(2, idVacancy);
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method insertVacancyAndProfile: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }
    @Override
    public boolean update(Vacancy item) throws DAOException {
        checkInput(item);
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_VACANCY_BY_ID);
            statement.setString(1,item.getVacancyTitle());
            statement.setString(2,item.getVacancyDescription());
            statement.setString(3,item.getLocation());
            statement.setBoolean(4,item.getVacancyStatus());
            statement.setString(5,item.getCompany());
            statement.setLong(6,item.getVacancyId());
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method update: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return deleteEntity(id,SQL_DELETE_VACANCY_BY_ID);

    }

    @Override
    public List<Vacancy> selectAll() throws DAOException {
        return selectVacancy(SQL_SELECT_ALL_VACANCY);
    }

    private Vacancy initVacancy(ResultSet resultSet) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId(resultSet.getLong("vacancy_id"));
        vacancy.setVacancyTitle(resultSet.getString("vacancy_title"));
        vacancy.setVacancyDescription(resultSet.getString("vacancy_description"));
        vacancy.setLocation(resultSet.getString("vacancy_location"));
        vacancy.setCompany(resultSet.getString("company"));
        vacancy.setVacancyStatus(resultSet.getBoolean("vacancy_status"));
        return vacancy;
    }

    @Override
    public boolean changeStatus(Long id) throws DAOException {
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID);
            statement.setLong(1,id);
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method changeStatus: ",e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return status;
    }

    @Override
    public List<Vacancy> selectActualVacancy() throws DAOException {
        return selectVacancy(SQL_SELECT_ACTUAL_VACANCY);
    }


}
