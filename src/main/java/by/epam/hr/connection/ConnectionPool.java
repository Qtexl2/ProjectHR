package by.epam.hr.connection;

import by.epam.hr.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class ConnectionPool.
 */
public class ConnectionPool{

    /** The pool shutting down. */
    private static AtomicBoolean poolShuttingDown;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    /** The instance. */
    private static ConnectionPool instance;

    /** The number attempts get connection. */
    private static int numberAttemptsGetConnection;

    /** The ms between attempts connection. */
    private static int msBetweenAttemptsConnection;

    /** How long to wait in second for a validation of connection (isValid(time)). */
    private static final int TIME_VALID_CON = 2;

    /** The lock instance. */
    private static Lock lockInstance = new ReentrantLock();

    /** The counter free connection. */
    private static AtomicInteger counterFreeConnection;

    /** The counter all connection. */
    private static AtomicInteger counterAllConnection;

    /** The pool created. */
    private static AtomicBoolean poolCreated = new AtomicBoolean(false);

    /** The increment connection. */
    private static AtomicLong incrementConnection;

    /** The time out force close. */
    private static int timeOutForceClose;

    /** The min size pool. */
    private static int minSizePool;

    /** The ms timer period. */
    private static int msTimerPeriod;

    /** The max size pool. */
    private static int maxSizePool;

    /** The min count free connections. */
    private static int minCountFreeConnections;

    /** The free connections. */
    private LinkedBlockingQueue<PooledConnection> freeConnections;

    /** The busy connections. */
    private ArrayBlockingQueue<PooledConnection> busyConnections;

    /** The config CP. */
    private ConnectionPoolConfig configCP;

    /**
     * Instantiates a new connection pool.
     */
    private ConnectionPool(){
        poolShuttingDown = new AtomicBoolean(false);
        configCP = new ConnectionPoolConfig();
        timeOutForceClose = configCP.getTimeOutForceClose();
        numberAttemptsGetConnection = configCP.getNumberAttemptsGetConnection();
        msBetweenAttemptsConnection = configCP.getMsBetweenAttemptsConnection();
        msTimerPeriod = configCP.getMsTimerPeriod();
        minCountFreeConnections = configCP.getMinCountFreeConnections();
        maxSizePool = configCP.getMaxConnections();
        minSizePool = configCP.getMinConnections();
        freeConnections = new LinkedBlockingQueue<>(maxSizePool);
        busyConnections = new ArrayBlockingQueue<>(maxSizePool);
        incrementConnection = new AtomicLong();
        counterFreeConnection = new AtomicInteger();
        counterAllConnection = new AtomicInteger();
        for (int i = 0; i < minSizePool; i++) {
            addConnection();
        }
        LOGGER.log(Level.INFO,"ConnectionPool was created with " + minSizePool + "connections");
    }

    /**
     * Close connection.
     *
     * @param conn the conn
     */
    private void closeConnection(PooledConnection conn){
        if(conn != null) {
            try {
                conn.destroy();
                counterAllConnection.decrementAndGet();
                counterFreeConnection.decrementAndGet();
                if (counterAllConnection.get() < 0) {
                    counterAllConnection.set(0);
                }
            } catch (ConnectionPoolException e) {
                LOGGER.log(Level.WARN, "Connection not closed " + conn, e);
            }
        }
    }

    /**
     * Destroy.
     */
    public void destroy() {
        if(poolShuttingDown.get())
        {
            return;
        }
        PooledConnection tempCon;
        poolShuttingDown.set(true);
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                tempCon = freeConnections.take();
                instance.closeConnection(tempCon);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, "Connection not closed ", e);
            }
        }
        configCP.destroy();
        LOGGER.log(Level.INFO,"ConnectionPool was destroyed");
    }

    /**
     * Gets the single instance of ConnectionPool.
     *
     * @return single instance of ConnectionPool
     */
    public static ConnectionPool getInstance(){
        if (!poolCreated.get()){
            lockInstance.lock();
            if(instance == null){
                try {
                    instance = new ConnectionPool();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTaskCheckConnections(instance,timer),1000,msTimerPeriod);
                    poolCreated.set(true);
                }
                finally {
                    lockInstance.unlock();
                }
            }


        }
        return instance ;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    void releaseConnection(PooledConnection connection){
        if(poolShuttingDown.get()){
            instance.closeConnection(connection);
            LOGGER.log(Level.WARN,"Pool is disabled!. The connection wasn't returned to the pool");

        }
        if(connection != null){
            try{
                if(!connection.isClosed()){
                    connection.setLastUsed(System.currentTimeMillis());
                    freeConnections.offer(connection);
                    busyConnections.remove(connection);
                    counterFreeConnection.incrementAndGet();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"The connection wasn't returned to the pool",e);
            }
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public PooledConnection getConnection() throws ConnectionPoolException {
        if(poolShuttingDown.get()){
            throw new ConnectionPoolException("Pool is disabled!");
        }
        PooledConnection connection;
        for (int i = 0; i < numberAttemptsGetConnection; i++) {
            try{
                if(counterFreeConnection.get() > 0){
                    connection = freeConnections.poll();
                    if(connection.isClosed() | connection.isLogicalClose()){
                        connection = getConnection();
                    }

                    else {
                        connection.getMetaData().getTableTypes();
                    }
                    counterFreeConnection.decrementAndGet();
                    busyConnections.offer(connection);
                    return connection;
                }
                LOGGER.log(Level.INFO, "Wait " + msBetweenAttemptsConnection + " ms until the next connection attempt");
                TimeUnit.MILLISECONDS.sleep(msBetweenAttemptsConnection);
            }
            catch (SQLException | InterruptedException e){
                LOGGER.log(Level.WARN, "Connection not returned " );
            }
        }
        throw new ConnectionPoolException("Pool is free!");
    }

    /**
     * Adds the connection.
     */
    private void addConnection() {
        if(poolShuttingDown.get()){
            return;
        }
        try {
            if(counterAllConnection.get() < maxSizePool){
                Connection connection = DriverManager.getConnection(configCP.getJdbcUrl(),configCP.getUsername(),configCP.getPassword());
                PooledConnection pooledConnection = new PooledConnection(connection, incrementConnection.incrementAndGet());
                pooledConnection.setLastUsed(System.currentTimeMillis());
                freeConnections.add(pooledConnection);
                counterFreeConnection.incrementAndGet();
                counterAllConnection.incrementAndGet();
                LOGGER.log(Level.INFO,"Create new connection " + pooledConnection );

            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"The pool could not create the connection",e);
        }
    }
    /**
     * Get size of pool.
     */
    public int getSize(){
        return freeConnections.size();
    }

    /**
     * The Class TimerTaskCheckConnections.
     */
    private static class TimerTaskCheckConnections extends TimerTask {

        /** The connection pool. */
        ConnectionPool connectionPool;

        /** The timer. */
        Timer timer;

        /**
         * Instantiates a new timer task check connections.
         *
         * @param connectionPool the connection pool
         * @param timer the timer
         */
        private TimerTaskCheckConnections(ConnectionPool connectionPool,Timer timer) {
            this.connectionPool = connectionPool;
            this.timer = timer;
        }

        /**
         * @see java.util.TimerTask#run()
         */
        public void run() {
            if(poolShuttingDown.get()){
                timer.cancel();
                LOGGER.log(Level.INFO,"poolShutting is true. Timer cancel");

            }
            else {
                Iterator<PooledConnection> conIterator = connectionPool.freeConnections.iterator();
                long now = System.currentTimeMillis();
                while (conIterator.hasNext()) {
                    PooledConnection con = conIterator.next();
                    try {
                        if (!con.isValid(TIME_VALID_CON)) {
                            conIterator.remove();
                            connectionPool.closeConnection(con);
                            LOGGER.log(Level.INFO, "Invalid connection will be closed " + con);
                        }
                        else if (counterFreeConnection.get() > minSizePool && con.getLastUsed() + timeOutForceClose < now) {
                            conIterator.remove();
                            connectionPool.closeConnection(con);
                            LOGGER.log(Level.INFO, "timeOutForceClose is over " + con);
                        }
                        else if(con.isLogicalClose()){
                            conIterator.remove();
                            connectionPool.closeConnection(con);
                            LOGGER.log(Level.INFO, "Logical close is true .Connection will be closed " + con);
                        }

                    } catch (SQLException e) {
                        LOGGER.log(Level.WARN, "Connection not closed " + con, e);
                    }
                }

                while (counterAllConnection.get() < minSizePool) {
                    ConnectionPool.getInstance().addConnection();
                }
                if (counterFreeConnection.get() < minCountFreeConnections) {
                    ConnectionPool.getInstance().addConnection();
                }
            }
        }
    }
}
