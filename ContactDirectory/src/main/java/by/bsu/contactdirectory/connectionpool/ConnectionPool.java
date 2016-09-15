package by.bsu.contactdirectory.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alexandra on 17.08.2016.
 *
 */
public class ConnectionPool {
	
	private static final int VALIDATION_TIMEOUT = 1;

    private static ConnectionPool instance = null;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);

    private int maximumConnections;
    // package access for daemon
    int  minimumConnections;
    // package access for daemon
    AtomicInteger currentConnections = new AtomicInteger(0);
    private Lock createLock = new ReentrantLock();
    // package access for daemon
    LinkedBlockingDeque<PoolConnection> connections = new LinkedBlockingDeque<>();

    private String url;
    private Properties connectionProperties;

    private KillerDaemon killerThread;

    private ConnectionPool(String propertiesPath) throws ConnectionPoolException {
        if (!loadDriver()){
            throw new RuntimeException("Driver is not loaded");
        }

        //ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(propertiesPath);
        //parseResourceBundle(resourceBundle);
        parseResourceBundle(null);

        try{
            initConnections();
        }catch (SQLException ex){
            throw new ConnectionPoolException("cannot connect to database");
        }
    }

    public static void start(String pathToProperties){
        if (!isCreate.get()) {
            lock.lock();
            if (instance == null) {
                try {
                    instance = new ConnectionPool(pathToProperties);
                    isCreate.set(true);
                    System.out.println("create instance");
                } catch (ConnectionPoolException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    lock.unlock();
                }
            } else {
                lock.unlock();
            }
        }
    }

    private void parseResourceBundle(ResourceBundle resourceBundle){
        ResourceBundleParser.parse(this, resourceBundle);
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private boolean loadDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            return true;
        } catch (SQLException  ex) {
            return false;
        }
    }

    private PoolConnection createConnection() throws SQLException{
        PoolConnection pl = new PoolConnection(DriverManager.getConnection(url, connectionProperties));
        currentConnections.incrementAndGet();
        return pl;
    }

    private void initConnections() throws SQLException{
        connections = new LinkedBlockingDeque<>();
        for (int i = 0; i < minimumConnections; i++) {
            connections.add(createConnection());
            System.out.println("Create connection");
        }
        killerThread = new KillerDaemon();
        killerThread.setDaemon(true);
        killerThread.start();
    }

    void putBack(PoolConnection connection){
        connections.add(connection);
    }

    public Connection getConnection() throws ConnectionPoolException{
        if (connections.isEmpty()){
            createLock.lock();
            if (currentConnections.get() < maximumConnections){
                try{
                    PoolConnection newConnection = createConnection();
                    createLock.unlock();
                    System.out.println("Create new specific connection");
                    return newConnection;
                }catch (SQLException ex){
                    // add log
                }
            }
            createLock.unlock();
        }
        try {
        	Connection cn = connections.takeLast();
        	try {
	        	if (!cn.isValid(VALIDATION_TIMEOUT)) {
	        	    try {
                        ((PoolConnection) cn).closeConnection();
                    } catch (SQLException ex) {}
	        		cn = createConnection();
	        	}
        	} catch (SQLException ex) {
        		//
        		throw new ConnectionPoolException(ex);
        	}
            return cn;
        }catch (InterruptedException ex){
            throw new ConnectionPoolException(ex);
        }
    }

    public void close(){
        int closedConnections = 0;
        maximumConnections = 0;
        PoolConnection pl;
        while (closedConnections != currentConnections.get()){
            try {
                pl = (PoolConnection) getConnection();
                pl.closeConnection();
                System.out.println("Close connection");
            }catch (ConnectionPoolException | SQLException ex){
                //добавить лог
                System.err.println(ex);
            }
            closedConnections ++;
            killerThread.stopKiller();
        }
    }

    public void restart() throws ConnectionPoolException {
        close();
        try {
            initConnections();
        }catch (SQLException ex){
            throw new ConnectionPoolException(ex);
        }
    }

    void setMaximumConnections(int maximumConnections) {
        this.maximumConnections = maximumConnections;
    }

    void setMinimumConnections(int minimumConnections) {
        this.minimumConnections = minimumConnections;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}