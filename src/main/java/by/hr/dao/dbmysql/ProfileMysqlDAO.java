package by.hr.dao.dbmysql;

import by.hr.connection.ConnectionPool;
import by.hr.connection.PooledConnection;
import by.hr.dao.AbstractDAO;
import by.hr.dao.InterviewDAO;
import by.hr.dao.ProfileDAO;
import by.hr.entity.EnglishLevel;
import by.hr.entity.Gender;
import by.hr.entity.Profile;
import by.hr.entity.Role;
import by.hr.exception.ConnectionPoolException;
import by.hr.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileMysqlDAO extends AbstractDAO implements ProfileDAO{
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SQL_SELECT_DIALOG = "SELECT  m.message_id,  p.first_name, p.last_name, " +
            "(m.profile_sender_id + mu.profile_reception_id)-? as id FROM message m " +
            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
            "INNER JOIN profile p ON (m.profile_sender_id + mu.profile_reception_id) - ? = p.profile_id " +
            "WHERE m.profile_sender_id = ? or mu.profile_reception_id = ? " +
            "GROUP BY p.email ";
    private static final String SQL_INSERT_PROFILE = "INSERT INTO profile " +
            "(profile.email, profile.password, profile.role) VALUES (?,?,?) ";

    private static final String SQL_DELETE_PROFILE = "DELETE FROM profile WHERE profile.profile_id=?";
    private static final String SQL_SELECT_PROFILE_BY_ID = "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p WHERE p.profile_id = ? ";
    private static final String SQL_SELECT_PROFILE_BY_EMAIL_AND_PASSWORD= "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p WHERE p.email = ? AND  p.password=?";

    private static final String SQL_SELECT_PROFILE = "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p ";
    private static final String SQL_UPDATE_PROFILE_BY_ID = "UPDATE profile p SET p.role=?, p.first_name=?, p.last_name=?, " +
            "p.phone=?, p.english_level=?, p.age=?, p.gender=?, p.current_position=?, " +
            "p.describe=?, p.pre_interview=?, p.technical_interview=?, p.status_interview=? WHERE p.profile_id=? ";

    @Override
    public boolean delete(Long id) throws DAOException {
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_PROFILE);
            statement.setLong(1,id);
            statement.executeUpdate();
            status = true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method delete: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }



    @Override
    public boolean insert(Profile item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }

        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_PROFILE);
            statement.setString(1, item.getEmail());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getRole().name().toLowerCase());
            statement.executeUpdate();
            status = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method insert(): ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return status;
    }

    public static void main(String[] args) throws DAOException, SQLException {
        ProfileMysqlDAO profileMysqlDAO = new ProfileMysqlDAO();

        System.out.println(profileMysqlDAO.authentication("gleb@gmail.com","saasd"));
//        System.out.println(profileMysqlDAO.selectAll());
        ConnectionPool.getInstance().destroy();
    }

    public  void insertFoto() throws DAOException, SQLException {
        String foto_sql = "UPDATE profile p SET p.foto=? WHERE p.profile_id=1 ";
        FileInputStream fis = null;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            File file = new File("src/main/resources/123.jpg");
            fis = new FileInputStream(file);
            statement = connection.prepareStatement(foto_sql);
            statement.setBinaryStream(1,fis, file.length());
            statement.executeUpdate();
            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(false);
            closeStatement(connection,statement);
        }
    }
    void readBLOB() throws DAOException {
        String foto_sql = "SELECT profile.foto FROM profile WHERE profile.profile_id =1";
        FileOutputStream fis = null;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            File file = new File("");/////////
            fis = new FileOutputStream(file);
            statement = connection.prepareStatement(foto_sql);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                InputStream is = res.getBinaryStream("foto");
                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0){
                    fis.write(buffer);
                }
            }

        } catch (ConnectionPoolException | SQLException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStatement(connection,statement);
        }
    }


    @Override
    public List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException {
        List<Profile> profiles = new ArrayList<>();
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_DIALOG);
            statement.setLong(1,idReceiver);
            statement.setLong(2,idReceiver);
            statement.setLong(3,idReceiver);
            statement.setLong(4,idReceiver);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Profile profile = new Profile();
                profile.setProfileID(result.getLong("id"));
                profiles.add(profile);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectProfileHaveDialog(): ",e);
        }finally {
            closeStatement(connection,statement);
        }
        return profiles;
    }


    @Override
    public Profile selectByID(Long id) throws DAOException {
        Profile profile = null;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_PROFILE_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                profile = init(result);
            }
            else {
                throw new DAOException("Exception in method selectByID. Profile ID not found");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectByID: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return profile;
    }

    @Override
    public List<Profile> selectAll() throws DAOException {
        List<Profile> profiles = new ArrayList<>();
        PooledConnection connection = null;
        Statement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SQL_SELECT_PROFILE);
            while (result.next()) {
                Profile profile;
                profile = init(result);
                profiles.add(profile);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method selectAll: ",e);
        } finally {
            closeStatement(connection,statement);
        }
        return profiles;
    }

    @Override
    public boolean update(Profile item) throws DAOException {
        if(item == null ){
            throw new DAOException("The input object is null");
        }
        boolean status = false;
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PROFILE_BY_ID);
            statement.setString(1, item.getRole().name());
            statement.setString(2, item.getFirstName());
            statement.setString(3, item.getLastName());
            statement.setString(4, item.getPhone());
            statement.setString(5, item.getEnglishLevel().name());
            statement.setInt(6, item.getAge());
            statement.setString(7, item.getGender().name());
            statement.setString(8, item.getCurrentPosition());
            statement.setString(9, item.getDescribe());
            statement.setString(10, item.getPreInterview());
            statement.setString(11, item.getTechnicalInterview());
            statement.setBoolean(12, item.getStatusInterview());
            statement.setLong(13, item.getProfileID());
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
    public Profile authentication(String email, String password) throws DAOException{
        Profile profile = new Profile();
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_PROFILE_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    profile = init(resultSet);
            } else {
                throw new DAOException("Error in authentication: no user in db with such email ");
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception in method authentication: " + e);
        } finally {
            closeStatement(connection,statement);
        }
        return profile;
    }

    private Profile init(ResultSet resultSet) throws SQLException {
        Profile profile = new Profile();
        profile.setProfileID(resultSet.getLong("profile_id"));
        profile.setEmail(resultSet.getString("email"));
        profile.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
        profile.setFirstName(resultSet.getString("first_name"));
        profile.setLastName(resultSet.getString("last_name"));
        profile.setPhone(resultSet.getString("phone"));

        String enLvl = resultSet.getString("english_level");
        String gender = resultSet.getString("gender");
        if(enLvl != null) {
            profile.setEnglishLevel(EnglishLevel.valueOf(enLvl.toUpperCase()));
        }
        if(gender != null){
            profile.setGender(Gender.valueOf(gender.toUpperCase()));
        }
        profile.setAge(resultSet.getInt("age"));
        profile.setCurrentPosition(resultSet.getString("current_position"));
        profile.setDescribe(resultSet.getString("describe"));
        profile.setPreInterview(resultSet.getString("pre_interview"));
        profile.setTechnicalInterview(resultSet.getString("technical_interview"));
        profile.setStatusInterview(resultSet.getBoolean("status_interview"));
//TODO resume and photo  Profile init

        return profile;
    }
}