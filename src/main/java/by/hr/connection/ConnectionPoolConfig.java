package by.hr.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

class ConnectionPoolConfig {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String USER = "dbUserName";
    private static final String PASSWORD = "dbPassword";
    private static final String URL = "dbUrl";
    private static final String MIN_CONNECTION = "dbMinConnections";
    private static final String MAX_CONNECTION = "dbMaxConnections";
    private static final String DB_DRIVER = "dbDriver";
    private static final String PATH_CONFIG_FILE = "src/main/resources/CPconf.properties";
    private static final String DEFAULT_MIN_CONNECTION = "5";
    private static final String DEFAULT_MAX_CONNECTION = "20";
    private static final String NUMBER_ATTEMPTS_GET_CONNECTION = "dbNumberAttemptsGetConnection";
    private static final String DEFAULT_NUMBER_ATTEMPTS_GET_CONNECTION = "5";
    private static final String MS_BETWEEN_ATTEMPTS_CONNECTION = "dbMsBetweenAttemptsConnection";
    private static final String DEFAULT_MS_BETWEEN_ATTEMPTS_CONNECTION = "1500";
    private static final String MS_TIMER_PERIOD = "dbMsTimerPeriod";
    private static final String DEFAULT_MS_TIMER_PERIOD = "3000";
    private static final String MIN_COUNT_FREE_CONNECTIONS = "dbMinCountFreeConnections";
    private static final String DEFAULT_MIN_FREE_CONNECTION = "1";
    private static final String TIME_OUT_FORCE_CLOSE = "dbTimeOutForceClose";
    private static final String DEFAULT_TIME_OUT_FORCE_CLOSE = "60";
    private int timeOutForceClose;
    private int minCountFreeConnections;
    private int numberAttemptsGetConnection;
    private int msBetweenAttemptsConnection;
    private int msTimerPeriod;
    private int minConnections;
    private int maxConnections;
    private String jdbcUrl;
    private String username;
    private String password;
    private Driver driver;


    ConnectionPoolConfig(String pathConfigFile){
        setProperties(pathConfigFile);

    }
    ConnectionPoolConfig(){
        setProperties(PATH_CONFIG_FILE);
    }
    private void setProperties(String pathConfigFile){
        try {
            Properties props = new Properties();
            FileInputStream config = new FileInputStream(pathConfigFile);
            props.load(config);
            minConnections = Integer.parseInt(props.getProperty(MIN_CONNECTION,DEFAULT_MIN_CONNECTION));
            maxConnections = Integer.parseInt(props.getProperty(MAX_CONNECTION,DEFAULT_MAX_CONNECTION));
            minCountFreeConnections = Integer.parseInt(props.getProperty(MIN_COUNT_FREE_CONNECTIONS,DEFAULT_MIN_FREE_CONNECTION));
            numberAttemptsGetConnection = Integer.parseInt(props.getProperty(NUMBER_ATTEMPTS_GET_CONNECTION, DEFAULT_NUMBER_ATTEMPTS_GET_CONNECTION));
            msBetweenAttemptsConnection = Integer.parseInt(props.getProperty(MS_BETWEEN_ATTEMPTS_CONNECTION, DEFAULT_MS_BETWEEN_ATTEMPTS_CONNECTION));
            msTimerPeriod = Integer.parseInt(props.getProperty(MS_TIMER_PERIOD, DEFAULT_MS_TIMER_PERIOD));
            timeOutForceClose = Integer.parseInt(props.getProperty(TIME_OUT_FORCE_CLOSE, DEFAULT_TIME_OUT_FORCE_CLOSE))*1000;
            jdbcUrl = props.getProperty(URL);
            username = props.getProperty(USER);
            password = props.getProperty(PASSWORD);
            driver = (Driver)Class.forName(props.getProperty(DB_DRIVER)).newInstance();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.FATAL,"ConnectionPoolConfig was not created", e);
            throw new RuntimeException();
        }

    }
    void destroy(){
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
            LOGGER.log(Level.INFO,"Driver deregister");
        } catch (SQLException e) {
            LOGGER.log(Level.WARN,"Driver deregister with problem", e);

        }
    }

    int getMinConnections() {
        return minConnections;
    }

    int getMaxConnections() {
        return maxConnections;
    }

    String getJdbcUrl() {
        return jdbcUrl;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    Driver getDriver() {
        return driver;
    }

    int getMinCountFreeConnections() {
        return minCountFreeConnections;
    }

    int getNumberAttemptsGetConnection() {
        return numberAttemptsGetConnection;
    }

    int getMsBetweenAttemptsConnection() {
        return msBetweenAttemptsConnection;
    }

    int getMsTimerPeriod() {
        return msTimerPeriod;
    }

    public int getTimeOutForceClose() {
        return timeOutForceClose;
    }
}
