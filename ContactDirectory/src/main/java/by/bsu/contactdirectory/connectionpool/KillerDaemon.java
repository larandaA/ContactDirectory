package by.bsu.contactdirectory.connectionpool;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Alexandra on 18.08.2016.
 */
class KillerDaemon extends Thread {

    private static final int SLEEP_TIME = 1000 *60 * 30;
    private static final int DOWNTIME = 1000 * 60 * 20;
    private AtomicBoolean close = new AtomicBoolean(false);

    @Override
    public void run() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        while (!close.get()) {
            try {
                Thread.sleep(SLEEP_TIME);
            }catch (InterruptedException ex) {
                // add log
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
                    System.out.println("Close");
                } catch (InterruptedException | SQLException ex) {
                    //add log
                }
            }
        }
    }

    void stopKiller(){
        close.set(true);
    }
}
