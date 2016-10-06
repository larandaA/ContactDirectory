package by.bsu.contactdirectory.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Alexandra on 18.08.2016.
 */
class KillerDaemon extends Thread {

    private static final int SLEEP_TIME = 1000 *60 * 30;
    private static final int DOWNTIME = 1000 * 60 * 20;
    private AtomicBoolean close = new AtomicBoolean(false);

    private static Logger logger = LogManager.getLogger(KillerDaemon.class);

    @Override
    public void run() {
        logger.info("Killer daemon started working.");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        while (!close.get()) {
            try {
                Thread.sleep(SLEEP_TIME);
            }catch (InterruptedException ex) {
                logger.warn("Killer daemon was interrupted.", ex);
            }
            if (connectionPool.currentConnections.get() == connectionPool.minimumConnections) {
                continue;
            }
            PoolConnection connection;
            while( (connection = connectionPool.connections.peekFirst()) != null &&
                    System.currentTimeMillis() - connection.getLastUseTime() > DOWNTIME &&
                    connectionPool.currentConnections.get() > connectionPool.minimumConnections) {
                try {
                    connectionPool.connections.takeFirst();
                    connection.closeConnection();
                    connectionPool.currentConnections.decrementAndGet();
                    logger.info("Connection closed.");
                } catch (InterruptedException | SQLException ex) {
                    logger.warn("Cannot close connection.", ex);
                }
            }
        }
    }

    void stopKiller(){
        logger.info("Killer daemon stopped warking.");
        close.set(true);
    }
}
