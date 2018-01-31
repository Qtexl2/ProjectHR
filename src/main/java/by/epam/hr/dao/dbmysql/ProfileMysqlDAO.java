package by.epam.hr.dao.dbmysql;

import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.dao.ProfileDAO;
import by.epam.hr.exception.ConnectionPoolException;
import by.epam.hr.exception.DAOException;
import by.epam.hr.model.EnglishLevel;
import by.epam.hr.model.Gender;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileMysqlDAO extends ProfileDAO {
    private static final Logger LOGGER = LogManager.getRootLogger();
//    private static final String SQL_SELECT_DIALOG = "SELECT  m.message_id,  p.first_name, p.last_name, " +
//            "(m.profile_sender_id + mu.profile_reception_id)-? as id FROM message m " +
//            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
//            "INNER JOIN profile p ON (m.profile_sender_id + mu.profile_reception_id) - ? = p.profile_id " +
//            "WHERE m.profile_sender_id = ? or mu.profile_reception_id = ? " +
//            "GROUP BY p.email ";
    private static final String SQL_SELECT_DIALOG = "SELECT  p.profile_id, p.email, p.first_name, p.last_name, p.photo, " +
            "(m.profile_sender_id + mu.profile_reception_id)-? as id FROM message m " +
            "INNER JOIN message_and_user mu ON m.message_id = mu.message_id " +
            "INNER JOIN profile p ON (m.profile_sender_id + mu.profile_reception_id) - ? = p.profile_id " +
            "WHERE m.profile_sender_id = ? or mu.profile_reception_id = ? " +
            "GROUP BY p.email ";
    private static final String SQL_INSERT_PROFILE = "INSERT INTO profile " +
            "(profile.email, profile.password, profile.role) VALUES (?,?,?) ";
    private static final String SQL_DELETE_PROFILE = "DELETE FROM profile WHERE profile.profile_id=?";
    private static final String SQL_SELECT_PROFILE_BY_ID = "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, p.company, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p WHERE p.profile_id = ? ";
    private static final String SQL_SELECT_PROFILE_BY_EMAIL_AND_PASSWORD= "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, p.company, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p WHERE p.email = ? AND  p.password=?";
    private static final String SQL_SELECT_PROFILE_BY_EMAIL = "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, p.company, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p WHERE p.email = ?";
    private static final String SQL_SELECT_PROFILE = "SELECT p.profile_id, p.email, p.role, p.first_name, " +
            "p.last_name, p.phone, p.english_level, p.age, p.gender, p.current_position, p.company, " +
            "p.describe, p.resume, p.photo, p.pre_interview, p.technical_interview, p.status_interview " +
            "FROM profile p ";
    private static final String SQL_UPDATE_PROFILE_BY_ID = "UPDATE profile p SET p.role=?, p.first_name=?, p.last_name=?, " +
            "p.phone=?, p.english_level=?, p.age=?, p.gender=?, p.current_position=?, " +
            "p.describe=?, p.pre_interview=?, p.technical_interview=?, p.status_interview=?, p.company=? WHERE p.profile_id=? ";
    private static final String SQL_UPDATE_PROFILE_AFTER_INTERVIEW = "UPDATE profile p SET p.english_level=?, " +
            "p.pre_interview=?, p.technical_interview=?  WHERE p.profile_id=? ";

    private static final String SQL_UPDATE_BASE_PROFILE_BY_ID = "UPDATE profile p SET p.first_name=?, p.last_name=?, " +
            "p.phone=?, p.age=?, p.gender=?, p.current_position=?, p.describe=?, p.company=? WHERE p.profile_id=? ";
    private static final String SQL_UPDATE_PHOTO = "UPDATE profile p SET p.photo=? WHERE p.profile_id=? ";

    private static final String SQL_SELECT_PHOTO = "SELECT profile.photo FROM profile WHERE profile.profile_id =?";

    public ProfileMysqlDAO(boolean isTransaction) throws DAOException {
        this.isTransaction = isTransaction;
    }

    public void setConnection(PooledConnection connection) {
        this.connection = connection;
    }

    public ProfileMysqlDAO(PooledConnection connection) {
        this.connection = connection;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return deleteEntity(id,SQL_DELETE_PROFILE);
    }

    @Override
    public boolean insert(Profile item) throws DAOException {
        checkInput(item);
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_INSERT_PROFILE);
            statement.setString(1, item.getEmail());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getRole().name().toLowerCase());
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method insert(): ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method insert(): ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return status;
    }

    public static void main(String[] args) throws DAOException, SQLException {
        ProfileMysqlDAO profileMysqlDAO = new ProfileMysqlDAO(false);
        System.out.println(profileMysqlDAO.selectProfileHaveDialog(null));
//        profileMysqlDAO.readBLOB();
//        Profile profile = new Profile("pds@gmail.com","fsd", Role.CANDIDATE);
//        profileMysqlDAO.insert(profile);
//        profileMysqlDAO.insert(profile1);
//        System.out.println(profileMysqlDAO.checkUser("gleb@gmail.com","saasd"));
//        profileMysqlDAO.delete(9L);
//        System.out.println(profileMysqlDAO.selectAll());
        ConnectionPool.getInstance().destroy();
    }

    public  boolean updatePhoto(Long id, InputStream is) throws DAOException{
        boolean status = false;
        checkTransaction();
        PreparedStatement statement = null;
        try{
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_UPDATE_PHOTO);
            statement.setBinaryStream(1,is);
            statement.setLong(2,id);
            statement.executeUpdate();
            connection.commit();
            status =true;

        } catch (SQLException e) {
            throw new DAOException("Exception in method selectPhoto",e);
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                connection.setLogicalClose(true);
                throw new DAOException("Exception in method selectPhoto",e);
            }
            finally {
                try{
                    closeStatement(statement);
                }
                catch (DAOException ex){
                    throw new DAOException("Exception in method selectPhoto ",ex);
                }
                finally {
                    closeConnection(connection);
                }
            }
        }
        return status;
    }
    public byte[] selectPhoto(Long id) throws DAOException {
        checkTransaction();
        PreparedStatement statement = null;
        byte[] out = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PHOTO);
            statement.setLong(1,id);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                Blob imageBlob = res.getBlob("photo");
                out = imageBlob.getBytes(1,(int)imageBlob.length());
            }
            return out;
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectPhoto",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method selectPhoto ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
    }


    @Override
    public List<Profile> selectProfileHaveDialog(Long idReceiver) throws DAOException {
        checkTransaction();
        List<Profile> profiles = new ArrayList<>();
        if(idReceiver == null){
            throw new DAOException("Exception in method selectProfileHaveDialog(): ");
        }
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(SQL_SELECT_DIALOG);
            statement.setLong(1,idReceiver);
            statement.setLong(2,idReceiver);
            statement.setLong(3,idReceiver);
            statement.setLong(4,idReceiver);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Profile profile = new Profile();
                profile.setProfileID(result.getLong("id"));
                profile.setEmail(result.getString("email"));
                profile.setFirstName(result.getString("first_name"));
                profile.setLastName(result.getString("last_name"));
                profile.setPhoto(result.getString("photo"));
                profiles.add(profile);
            }
            System.out.println(profiles);
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectProfileHaveDialog(): ",e);
        }finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method selectProfileHaveDialog():",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return profiles;
    }
    @Override
    public Profile selectByID(Long id) throws DAOException {
        checkTransaction();
        Profile profile = null;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_PROFILE_BY_ID);
            statement.setLong(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                profile = init(result);
            }
            else {
                throw new DAOException("Exception in method selectByID. Profile ID not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectByID: ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method selectByID(): ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return profile;
    }

    @Override
    public List<Profile> selectAll() throws DAOException {
        checkTransaction();
        List<Profile> profiles = new ArrayList<>();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SQL_SELECT_PROFILE);
            while (result.next()) {
                Profile profile;
                profile = init(result);
                profiles.add(profile);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method selectAll: ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method selectAll: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return profiles;
    }
    @Override
    public boolean updateBaseProfile(Profile item) throws DAOException {
        checkInput(item);
        checkTransaction();

        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_BASE_PROFILE_BY_ID);
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setString(3, item.getPhone());
            statement.setInt(4, item.getAge());
            statement.setString(5, item.getGender().name());
            statement.setString(6, item.getCurrentPosition());
            statement.setString(7, item.getDescribe());
            statement.setString(8, item.getCompany());
            statement.setLong(9, item.getProfileID());

            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method updateBaseProfile: ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method updateBaseProfile: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return status;
    }
    @Override
    public boolean update(Profile item) throws DAOException {
        checkTransaction();
        checkInput(item);
        boolean status = false;
        PreparedStatement statement = null;
        try{
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
            statement.setString(13, item.getCompany());
            statement.setLong(14, item.getProfileID());
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method update: ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method update: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return status;
    }
    public boolean updateAfterInterview(EnglishLevel englishLevel, String preInterview, String techInterview, Long id)
            throws DAOException {
        checkTransaction();
        boolean status = false;
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_UPDATE_PROFILE_AFTER_INTERVIEW);
            statement.setString(1, englishLevel.name());
            statement.setString(2, preInterview);
            statement.setString(3, techInterview);
            statement.setLong(4, id);
            statement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            throw new DAOException("Exception in method updateAfterInterview: ",e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method update: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return status;
    }



    @Override
    public Profile checkUser(String email, String password) throws DAOException{
        Profile profile;
        checkTransaction();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PROFILE_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                    profile = init(resultSet);
            } else {
                throw new DAOException("Error in checkUser: no user in db with such email ");
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method checkUser: " + e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method checkUser: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return profile;
    }
    @Override
    public boolean checkFreeEmail(String email) throws DAOException{
        boolean status = true;
        checkTransaction();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PROFILE_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = false;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in method checkFreeEmail: " + e);
        } finally {
            try{
                closeStatement(statement);
            }
            catch (DAOException ex){
                throw new DAOException("Exception in method checkFreeEmail: ",ex);
            }
            finally {
                closeConnection(connection);
            }
        }
        return status;
    }

    private Profile init(ResultSet resultSet) throws SQLException {
        Profile profile = new Profile();
        profile.setProfileID(resultSet.getLong("profile_id"));
        profile.setEmail(resultSet.getString("email"));
        profile.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
        profile.setFirstName(resultSet.getString("first_name"));
        profile.setLastName(resultSet.getString("last_name"));
        profile.setCompany(resultSet.getString("company"));
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
        profile.setPhoto(resultSet.getString("photo"));
        profile.setResume(resultSet.getString("resume"));
        return profile;
    }
}
