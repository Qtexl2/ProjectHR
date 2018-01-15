package by.hr.dao.dbmysql;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.dao.AbstractDAO;
import by.hr.dao.VacancyDAO;
import by.hr.entity.Vacancy;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VacancyMysqlDAO extends AbstractDAO<Long,Vacancy> implements VacancyDAO{

    private final static String SQL_DELETE_VACANCY_BY_ID = "DELETE FROM vacancy WHERE vacancy.vacancy_id=?";

    private static final String SQL_UPDATE_VACANCY_BY_ID = "UPDATE vacancy SET vacancy.vacancy_title=?, " +
            "vacancy.vacancy_description=?, vacancy.vacancy_location=?, vacancy.vacancy_status=? WHERE vacancy.vacancy_id=?";

    private static final String SQL_UPDATE_STATUS_BY_ID = "UPDATE vacancy SET " +
            "vacancy.vacancy_status= not vacancy.vacancy_status  WHERE vacancy.vacancy_id=?";

    private static final String SQL_INSERT_VACANCY = "INSERT INTO vacancy " +
            "(vacancy.vacancy_title, vacancy.vacancy_description, vacancy.vacancy_location) VALUES(?,?,?)";

    private static final String SQL_SELECT_ALL_VACANCY = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status FROM vacancy ";

    private static final String SQL_SELECT_ACTUAL_VACANCY = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status FROM vacancy WHERE vacancy.vacancy_status=1";

    private static final String SQL_SELECT_VACANCY_BY_ID = "SELECT  vacancy.vacancy_id, vacancy.vacancy_title, " +
            "vacancy.vacancy_description, vacancy.vacancy_location, vacancy.vacancy_status FROM vacancy " +
            "WHERE vacancy.vacancy_id=?";
    private static final String SQL_SELECT_BY_TITLE_AND_LOCAL_AND_DESC = "SELECT * FROM hr.vacancy WHERE " +
            "(vacancy.vacancy_title LIKE concat('%',replace(?,' ','%'),'%') " +
            "OR vacancy.vacancy_description LIKE concat('%',replace(?,' ','%'),'%')) " +
            "AND vacancy.vacancy_location LIKE concat('%',?,'%') " +
            "AND vacancy.vacancy_status=true";

    private List<Vacancy> selectVacancy(String sql) throws DAOException {
        List<Vacancy> vacancies = new ArrayList<>();
        PooledConnection connection = null;
        Statement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Vacancy vacancy;
                vacancy = initVacancy(result);
                vacancies.add(vacancy);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectVacancy(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return vacancies;
    }

    @Override
    public Vacancy selectByID(Long id) throws DAOException {
        Vacancy vacancy = null;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_VACANCY_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                vacancy = initVacancy(result);
            }
            else {
                throw new DAOException("Exception in method selectByID(). Vacancy ID not found");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectByID(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> selectVacancyByLocAndTitle(String job, String location) throws DAOException{
        List<Vacancy> vacancies = new ArrayList<>();
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
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

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectVacancyByLocAndTitle(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return vacancies;
    }
    @Override
    public boolean insert(Vacancy item) throws DAOException{
        checkInput(item);
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_VACANCY);
            statement.setString(1,item.getVacancyTitle());
            statement.setString(2,item.getVacancyDescription());
            statement.setString(3,item.getLocation());
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method insert: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    @Override
    public boolean update(Vacancy item) throws DAOException {
        checkInput(item);
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_VACANCY_BY_ID);
            statement.setString(1,item.getVacancyTitle());
            statement.setString(2,item.getVacancyDescription());
            statement.setString(3,item.getLocation());
            statement.setBoolean(4,item.getVacancyStatus());
            statement.setLong(5,item.getVacancyID());
            statement.executeUpdate();
            status = true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method update: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return deleteQuery(id,SQL_DELETE_VACANCY_BY_ID);

    }


    @Override
    public List<Vacancy> selectAll() throws DAOException {
        return selectVacancy(SQL_SELECT_ALL_VACANCY);
    }

    private Vacancy initVacancy(ResultSet resultSet) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyID(resultSet.getLong("vacancy_id"));
        vacancy.setVacancyTitle(resultSet.getString("vacancy_title"));
        vacancy.setVacancyDescription(resultSet.getString("vacancy_description"));
        vacancy.setLocation(resultSet.getString("vacancy_location"));
        vacancy.setVacancyStatus(resultSet.getBoolean("vacancy_status"));
        return vacancy;
    }

    public static void main(String[] args) throws DAOException {
        VacancyMysqlDAO vacancyMysqlDAO = new VacancyMysqlDAO();
        vacancyMysqlDAO.delete(6L);
//        Vacancy vacancy = new Vacancy("Java Junior", "Need developer in Epam","Minsk");
//        vacancy.setVacancyStatus(true);
//        vacancy.setVacancyID(6L);
//        vacancyMysqlDAO.deleteByID(7L);
//        vacancyMysqlDAO.deleteByID(8L);
//        vacancyMysqlDAO.updateByID(6L,"DEVkaKAKA","DEVKAKA",null,false);
//        vacancyMysqlDAO.update(vacancy);
//        System.out.println(vacancyMysqlDAO.selectAll());
//        System.out.println(vacancyMysqlDAO.selectActualVacancy());
//        vacancyMysqlDAO.changeStatus(7L);
//        vacancyMysqlDAO.changeStatus(1L);
//        System.out.println(vacancyMysqlDAO.selectVacancyByLocAndTitle("dev",""));
//        System.out.println(vacancyMysqlDAO.selectByID(10L));
        ConnectionPool.getInstance().destroy();
    }

    @Override
    public boolean changeStatus(Long id) throws DAOException {
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID);
            statement.setLong(1,id);
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
    public List<Vacancy> selectActualVacancy() throws DAOException {
        return selectVacancy(SQL_SELECT_ACTUAL_VACANCY);
    }


}
