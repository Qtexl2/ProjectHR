package connection;

import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.connection.PooledConnection;
import by.epam.hr.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class ConnectionPoolTest {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private ConnectionPool connectionPool;
    @BeforeClass
    public void initPoolTest() {
        try {
            connectionPool = ConnectionPool.getInstance();
            Assert.assertNotNull(connectionPool);
        } catch (Exception e) {
            LOGGER.info("Error in ConnectionPool.getInstance()");
        }
    }

    @Test
    public void poolSizeTest() throws ConnectionPoolException, InterruptedException {
        int size = 3;
        LOGGER.log(Level.INFO,"Get and return connections ...");
        PooledConnection pooledConnection1 = connectionPool.getConnection();
        PooledConnection pooledConnection2 = connectionPool.getConnection();
        PooledConnection pooledConnection3 = connectionPool.getConnection();
        PooledConnection pooledConnection4 = connectionPool.getConnection();
        PooledConnection pooledConnection5 = connectionPool.getConnection();
        PooledConnection pooledConnection6 = connectionPool.getConnection();
        PooledConnection pooledConnection7 = connectionPool.getConnection();
        pooledConnection1.close();
        pooledConnection2.close();
        pooledConnection3.close();
        pooledConnection4.close();
        pooledConnection5.close();
        pooledConnection6.close();
        pooledConnection7.close();
        if(connectionPool.getSize() != 7){
            Assert.fail("ConnectionPool Failed");
        }
        TimeUnit.SECONDS.sleep(45);
        if(connectionPool.getSize() != 3){
            Assert.fail("ConnectionPool Failed");
        }
        pooledConnection4 = connectionPool.getConnection();
        pooledConnection4.setLogicalClose(true);
        TimeUnit.SECONDS.sleep(5);
        if(connectionPool.getSize() != 2){
            Assert.fail("ConnectionPool Failed");
        }
    }
    @Test(dependsOnMethods ={"poolSizeTest"}, expectedExceptions = ConnectionPoolException.class)
    public void destroyPoolTest() throws ConnectionPoolException {
        ConnectionPool.getInstance().destroy();
        ConnectionPool.getInstance().getConnection();
    }


}
