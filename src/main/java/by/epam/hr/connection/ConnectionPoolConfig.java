package by.epam.hr.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The Class ConnectionPoolConfig.
 */
class ConnectionPoolConfig {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolConfig.class);

    /** The Constant USER. */
    private static final String USER = "dbUserName";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "dbPassword";

    /** The Constant URL. */
    private static final String URL = "dbUrl";

    /** The Constant MIN_CONNECTION. */
    private static final String MIN_CONNECTION = "dbMinConnections";

    /** The Constant MAX_CONNECTION. */
    private static final String MAX_CONNECTION = "dbMaxConnections";

    /** The Constant DB_DRIVER. */
    private static final String DB_DRIVER = "dbDriver";

    /** The Constant PATH_CONFIG_FILE. */
    private static final String PATH_CONFIG_FILE = "CPconf.properties";

    /** The Constant DEFAULT_MIN_CONNECTION. */
    private static final String DEFAULT_MIN_CONNECTION = "5";

    /** The Constant DEFAULT_MAX_CONNECTION. */
    private static final String DEFAULT_MAX_CONNECTION = "20";

    /** The Constant NUMBER_ATTEMPTS_GET_CONNECTION. */
    private static final String NUMBER_ATTEMPTS_GET_CONNECTION = "dbNumberAttemptsGetConnection";

    /** The Constant DEFAULT_NUMBER_ATTEMPTS_GET_CONNECTION. */
    private static final String DEFAULT_NUMBER_ATTEMPTS_GET_CONNECTION = "5";

    /** The Constant MS_BETWEEN_ATTEMPTS_CONNECTION. */
    private static final String MS_BETWEEN_ATTEMPTS_CONNECTION = "dbMsBetweenAttemptsConnection";

    /** The Constant DEFAULT_MS_BETWEEN_ATTEMPTS_CONNECTION. */
    private static final String DEFAULT_MS_BETWEEN_ATTEMPTS_CONNECTION = "1500";

    /** The Constant MS_TIMER_PERIOD. */
    private static final String MS_TIMER_PERIOD = "dbMsTimerPeriod";

    /** The Constant DEFAULT_MS_TIMER_PERIOD. */
    private static final String DEFAULT_MS_TIMER_PERIOD = "3000";

    /** The Constant MIN_COUNT_FREE_CONNECTIONS. */
    private static final String MIN_COUNT_FREE_CONNECTIONS = "dbMinCountFreeConnections";

    /** The Constant DEFAULT_MIN_FREE_CONNECTION. */
    private static final String DEFAULT_MIN_FREE_CONNECTION = "1";

    /** The Constant TIME_OUT_FORCE_CLOSE. */
    private static final String TIME_OUT_FORCE_CLOSE = "dbTimeOutForceClose";

    /** The Constant DEFAULT_TIME_OUT_FORCE_CLOSE. */
    private static final String DEFAULT_TIME_OUT_FORCE_CLOSE = "60";

    /** The time out force close. */
    private int timeOutForceClose;

    /** The min count free connections. */
    private int minCountFreeConnections;

    /** The number attempts get connection. */
    private int numberAttemptsGetConnection;

    /** The ms between attempts connection. */
    private int msBetweenAttemptsConnection;

    /** The ms timer period. */
    private int msTimerPeriod;

    /** The min connections. */
    private int minConnections;

    /** The max connections. */
    private int maxConnections;

    /** The jdbc url. */
    private String jdbcUrl;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The driver. */
    private Driver driver;


    /**
     * Instantiates a new connection pool config.
     *
     * @param pathConfigFile the path config file
     */
    ConnectionPoolConfig(String pathConfigFile){
        setProperties(pathConfigFile);

    }

    /**
     * Instantiates a new connection pool config.
     */
    ConnectionPoolConfig(){
        setProperties(PATH_CONFIG_FILE);
    }

    /**
     * Sets the properties.
     *
     * @param pathConfigFile the new properties
     */
    private void setProperties(String pathConfigFile){
        try {
            Properties props = new Properties();
            props.load(ConnectionPoolConfig.class.getClassLoader().getResourceAsStream(pathConfigFile));

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
            LOGGER.log(Level.INFO,"ConnectionPoolConfig was created ");

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.FATAL,"ConnectionPoolConfig was not created", e);
            throw new RuntimeException();
        }

    }

    /**
     * Destroy.
     */
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

    /**
     * Gets the min connections.
     *
     * @return the min connections
     */
    int getMinConnections() {
        return minConnections;
    }

    /**
     * Gets the max connections.
     *
     * @return the max connections
     */
    int getMaxConnections() {
        return maxConnections;
    }

    /**
     * Gets the jdbc url.
     *
     * @return the jdbc url
     */
    String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    String getPassword() {
        return password;
    }

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    Driver getDriver() {
        return driver;
    }

    /**
     * Gets the min count free connections.
     *
     * @return the min count free connections
     */
    int getMinCountFreeConnections() {
        return minCountFreeConnections;
    }

    /**
     * Gets the number attempts get connection.
     *
     * @return the number attempts get connection
     */
    int getNumberAttemptsGetConnection() {
        return numberAttemptsGetConnection;
    }

    /**
     * Gets the ms between attempts connection.
     *
     * @return the ms between attempts connection
     */
    int getMsBetweenAttemptsConnection() {
        return msBetweenAttemptsConnection;
    }

    /**
     * Gets the ms timer period.
     *
     * @return the ms timer period
     */
    int getMsTimerPeriod() {
        return msTimerPeriod;
    }

    /**
     * Gets the time out force close.
     *
     * @return the time out force close
     */
    int getTimeOutForceClose() {
        return timeOutForceClose;
    }
}
