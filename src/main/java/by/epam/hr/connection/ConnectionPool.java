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


public class ConnectionPool{
    private static AtomicBoolean poolShuttingDown;
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static ConnectionPool instance;
    private static int numberAttemptsGetConnection;
    private static int msBetweenAttemptsConnection;
    /**
     * How long to wait in second for a validation of connection (isValid(time))
     */
    private static final int TIME_VALID_CON = 2;
    private static Lock lockInstance = new ReentrantLock();
    private static AtomicInteger counterFreeConnection;
    private static AtomicInteger counterAllConnection;
    private static AtomicLong incrementConnection;
    private static int timeOutForceClose;
    private static int minSizePool;
    private static int msTimerPeriod;
    private static int maxSizePool;
    private static int minCountFreeConnections;
    private LinkedBlockingQueue<PooledConnection> freeConnections;
    private ArrayBlockingQueue<PooledConnection> busyConnections;// обычную
    private ConnectionPoolConfig configCP;

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
    }

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

    public void destroy() {
        if(poolShuttingDown.get())
        {
            return;
        }
        PooledConnection tempCon;
        poolShuttingDown.set(true);
        while (!freeConnections.isEmpty()){//take for
            tempCon = freeConnections.poll();
            instance.closeConnection(tempCon);
        }

            configCP.destroy();
    }
    public static void main(String[] args) throws ConnectionPoolException, InterruptedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PooledConnection pooledConnection1 = connectionPool.getConnection();
        PooledConnection pooledConnection2 = connectionPool.getConnection();
        PooledConnection pooledConnection3 = connectionPool.getConnection();
        PooledConnection pooledConnection4 = connectionPool.getConnection();
        pooledConnection3.setLogicalClose(true);
        TimeUnit.SECONDS.sleep(10);
        connectionPool.releaseConnection(pooledConnection1);
        connectionPool.releaseConnection(pooledConnection2);
        connectionPool.releaseConnection(pooledConnection3);
        connectionPool.releaseConnection(pooledConnection4);
        TimeUnit.SECONDS.sleep(10);
        pooledConnection1 = connectionPool.getConnection();
        pooledConnection2 = connectionPool.getConnection();
        pooledConnection3 = connectionPool.getConnection();
        pooledConnection4 = connectionPool.getConnection();
        TimeUnit.SECONDS.sleep(10);
        connectionPool.releaseConnection(pooledConnection1);
        connectionPool.releaseConnection(pooledConnection2);
        connectionPool.releaseConnection(pooledConnection3);
        connectionPool.releaseConnection(pooledConnection4);
        TimeUnit.SECONDS.sleep(10);
        connectionPool.destroy();

//        pooledConnection3 = connectionPool.getConnection();

    }

    public static ConnectionPool getInstance(){

        if (instance == null){//atomic
            lockInstance.lock();
                if(instance == null){
                    try {
                        instance = new ConnectionPool();
                        Timer timer = new Timer();
                        timer.schedule(new TimerTaskCheckConnections(instance,timer),1000,msTimerPeriod);
                        //
                    }
                    finally {
                        lockInstance.unlock();
                    }
                }
            }
        return instance ;
    }

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
                    System.out.println("Возврат соединения " + connection);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"The connection wasn't returned to the pool",e);
            }
        }
    }

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
                    System.out.println("Соеднинение заюзано " + connection);
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

    private static class TimerTaskCheckConnections extends TimerTask {
        ConnectionPool connectionPool;
        Timer timer;

        private TimerTaskCheckConnections(ConnectionPool connectionPool,Timer timer) {
            this.connectionPool = connectionPool;
            this.timer = timer;
        }

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
                            counterFreeConnection.decrementAndGet();
                            LOGGER.log(Level.INFO, "Invalid connection will be closed " + con);
                        }
                        else if (counterFreeConnection.get() > minSizePool & con.getLastUsed() + timeOutForceClose < now) {
                            conIterator.remove();
                            connectionPool.closeConnection(con);
                            counterFreeConnection.decrementAndGet();
                            LOGGER.log(Level.INFO, "timeOutForceClose is over " + con);
                        }
                        else if(con.isLogicalClose()){
                            conIterator.remove();
                            connectionPool.closeConnection(con);
                            counterFreeConnection.decrementAndGet();
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
