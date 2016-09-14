package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by Alexandra on 05.09.2016.
 */
public class AddressDao extends AbstractDao {

    private static AddressDao instance = new AddressDao();

    private static final String CREATE = "INSERT INTO `address`(`country_id`, `city`, `local_address`, `index`, `contact_id`) " +
            "VALUES ((SELECT `id` FROM `country` WHERE `name` = ?), ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE `address` SET `country_id` = (SELECT `id` FROM `country` WHERE `name` = ?), " +
            "`city` = ?, `local_address` = ?, `index` = ? WHERE `contact_id` = ?;";
    private static final String DELETE = "DELETE FROM `address` WHERE `contact_id` = ?;";
    private static final String SELECT = "SELECT `name`, `city`, `local_address`, `index`, `contact_id` FROM `address` " +
            "LEFT JOIN `country` ON `address`.`country_id` = `country`.`id` WHERE `contact_id` = ? LIMIT 1;";

    private AddressDao() {}

    public static AddressDao getInstance() { return instance; }

    private Address parse(ResultSet rs) throws SQLException {
        Address address = new Address();

        address.setCountry(rs.getString(1));
        address.setCity(rs.getString(2));
        address.setLocalAddress(rs.getString(3));
        address.setIndex(rs.getString(4));
        address.setContactId(rs.getInt(5));

        return address;
    }

    public void create(Address address) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(CREATE)) {
            if(address.getCountry() == null) {
            	st.setNull(1, Types.VARCHAR);
            } else {
            	st.setString(1, address.getCountry());
            }
            if(address.getCity() == null) {
            	st.setNull(2, Types.VARCHAR);
            } else {
            	st.setString(2, address.getCity());
            }
            if(address.getLocalAddress() == null) {
            	st.setNull(3, Types.VARCHAR);
            } else {
            	st.setString(3, address.getLocalAddress());
            }
            if(address.getIndex() == null) {
            	st.setNull(4, Types.VARCHAR);
            } else {
            	st.setString(4, address.getIndex());
            }
            st.setInt(5, address.getContactId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public void update(Address address) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(UPDATE)) {
            if(address.getCountry() == null) {
            	st.setNull(1, Types.VARCHAR);
            } else {
            	st.setString(1, address.getCountry());
            }
            if(address.getCity() == null) {
            	st.setNull(2, Types.VARCHAR);
            } else {
            	st.setString(2, address.getCity());
            }
            if(address.getLocalAddress() == null) {
            	st.setNull(3, Types.VARCHAR);
            } else {
            	st.setString(3, address.getLocalAddress());
            }
            if(address.getIndex() == null) {
            	st.setNull(4, Types.VARCHAR);
            } else {
            	st.setString(4, address.getIndex());
            }
            st.setInt(5, address.getContactId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    public void delete(int contactId) throws DaoException {
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(DELETE)) {
            st.setInt(1, contactId);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } 
    }

    public Address findById(int contactId) throws DaoException {
        Address address = null;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT)) {            
            st.setInt(1, contactId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                address = parse(rs);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return address;
    }

}
