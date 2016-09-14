package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.connectionpool.ConnectionPool;
import by.bsu.contactdirectory.connectionpool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alexandra on 04.09.2016.
 */
public abstract class AbstractDao {

    /*static {
        ConnectionPool.start("src/main/resources/db.properties");
    }*/

    protected Connection getConnection() throws DaoException {
        try {
            return ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
    }

    protected void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                //add log
            }
        }
    }

    protected void closeConnection(Connection cn) {
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException ex) {
                //add log
            }
        }
    }
}