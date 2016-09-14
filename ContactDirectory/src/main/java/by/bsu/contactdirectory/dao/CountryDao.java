package by.bsu.contactdirectory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 05.09.2016.
 */
public class CountryDao extends AbstractDao {

    private static CountryDao instance= new CountryDao();

    private static final String FIND_BY_NAME = "SELECT `id` FROM `country` WHERE `name` = ? LIMIT 1;";
    private static final String FIND_BY_CODE = "SELECT `id` FROM `country` WHERE `code` = ? LIMIT 1;";
    private static final String GET_NAMES = "SELECT `name` FROM `country`;";
    private static final String GET_CODES = "SELECT `code` FROM `country`;";

    private CountryDao() {}

    public static CountryDao getInstance() { return instance; }

    public int findByName(String name) throws DaoException {
        int id = -1;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(FIND_BY_NAME)) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } 
        return id;
    }

    public int findByCode(int code) throws DaoException {
        int id = -1;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(FIND_BY_CODE)) {
            st.setInt(1, code);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return id;
    }

    public List<Integer> getCodes() throws DaoException {
        LinkedList<Integer> codes = new LinkedList<Integer>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(GET_CODES)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                codes.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return codes;
    }

    public List<String> getNames() throws DaoException {
        LinkedList<String> names = new LinkedList<String>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(GET_NAMES)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                names.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return names;
    }

}
