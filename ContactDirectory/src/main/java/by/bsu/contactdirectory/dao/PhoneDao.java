package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Phone;
import by.bsu.contactdirectory.entity.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 05.09.2016.
 */
public class PhoneDao extends AbstractDao {

    private static PhoneDao instance = new PhoneDao();

    private static final String CREATE = "INSERT INTO `phone`(`country_id`, `operator_code`, `phone_number`, `type`, `comment`, " +
            "`contact_id`) VALUES ((SELECT `id` FROM `country` WHERE `code` = ?), ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE `phone` SET `country_id` = (SELECT `id` FROM `country` WHERE `code` = ?), " +
            "`operator_code` = ?, `phone_number` = ?, `type` = ?, `comment` = ? WHERE `id` = ?;";
    private static final String DELETE = "DELETE FROM `phone` WHERE `id` = ?;";
    private static final String SELECT = "SELECT `phone`.`id`, `code`, `operator_code`, `phone_number`, `type`, `comment`, " +
            "`contact_id` FROM `phone` LEFT JOIN `country` ON `phone`.`country_id` = `country`.`id`";
    private static final String SELECT_LIST = SELECT + " WHERE `contact_id` = ?;";
    private static final String SELECT_SINGLE = SELECT + " WHERE `phone`.`id` = ? LIMIT 1;";

    private PhoneDao() {}

    public static PhoneDao getInstance() { return instance; }

    private Phone parse(ResultSet rs) throws SQLException {
        Phone phone = new Phone();

        phone.setId(rs.getInt(1));
        phone.setCountryCode(rs.getInt(2));
        phone.setOperatorCode(rs.getInt(3));
        phone.setPhoneNumber(rs.getInt(4));
        
        String buf = rs.getString(5);
        if (buf == null) {
        	phone.setType(null);
        } else {
        	phone.setType(PhoneType.valueOf(buf));
        }
        phone.setComment(rs.getString(6));
        phone.setContactId(rs.getInt(7));

        return phone;
    }

    public void create(Phone phone, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(CREATE)) {
            if (phone.getCountryCode() == null) {
            	st.setNull(1, Types.INTEGER);
            } else {
            	st.setInt(1, phone.getCountryCode());
            }
            if (phone.getOperatorCode() == null) {
            	st.setNull(2, Types.INTEGER);
            } else {
            	st.setInt(2, phone.getOperatorCode());
            }
            if (phone.getPhoneNumber() == null) {
            	st.setNull(3, Types.INTEGER);
            } else {
            	st.setInt(3, phone.getPhoneNumber());
            }
            if (phone.getType() == null) {
            	st.setNull(4, Types.OTHER);
            } else {
            	st.setString(4, phone.getType().toString());
            }
            if (phone.getComment() == null) {
            	st.setNull(5, Types.VARCHAR);
            } else {
            	st.setString(5, phone.getComment());
            }            
            st.setInt(6, phone.getContactId());

            st.executeUpdate();
        }
    }

    void update(Phone phone, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(UPDATE)) {
            if (phone.getCountryCode() == null) {
            	st.setNull(1, Types.INTEGER);
            } else {
            	st.setInt(1, phone.getCountryCode());
            }
            if (phone.getOperatorCode() == null) {
            	st.setNull(2, Types.INTEGER);
            } else {
            	st.setInt(2, phone.getOperatorCode());
            }
            if (phone.getPhoneNumber() == null) {
            	st.setNull(3, Types.INTEGER);
            } else {
            	st.setInt(3, phone.getPhoneNumber());
            }
            if (phone.getType() == null) {
            	st.setNull(4, Types.OTHER);
            } else {
            	st.setString(4, phone.getType().toString());
            }
            if (phone.getComment() == null) {
            	st.setNull(5, Types.VARCHAR);
            } else {
            	st.setString(5, phone.getComment());
            }            
            st.setInt(6, phone.getId());

            st.executeUpdate();
        }
    }

    public void delete(int id, Connection cn) throws SQLException {
        try (PreparedStatement st = cn.prepareStatement(DELETE)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    public Phone findById(int id) throws DaoException {
        Phone phone = null;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_SINGLE)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                phone = parse(rs);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return phone;
    }

    public List<Phone> findByContact(int contactId) throws DaoException {
        LinkedList<Phone> phones = new LinkedList<Phone>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT_LIST)){
            st.setInt(1, contactId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                phones.add(parse(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return phones;
    }
}
