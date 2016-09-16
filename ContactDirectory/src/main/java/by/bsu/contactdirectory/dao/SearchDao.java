package by.bsu.contactdirectory.dao;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.SearchObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class SearchDao extends AbstractDao {

    private static SearchDao instance = new SearchDao();

    private static final String FROM = "FROM `contact` " +
            "LEFT JOIN `country` as `citizenship` ON `contact`.`country_id` = `citizenship`.`id` " +
            "LEFT JOIN `address` ON `contact`.`id` = `address`.`contact_id` " +
            "LEFT JOIN `country` as `addr_country` ON `address`.`country_id` = `addr_country`.`id`";
    private static final String COUNT = "SELECT COUNT(`contact`.`id`) " + FROM;
    private static final String SELECT = "SELECT `contact`.`id`, `first_name`, `last_name`, `patronymic`, `birth_date`, `gender`, " +
            "`citizenship`.`name`, `marital_status`, `web_site`, `email`, `place_of_work` " + FROM;
    private static final String LIMIT = " LIMIT ?, ?;";

    private SearchDao() {}

    public static SearchDao getInstance() { return instance; }

    public int setParameters(SearchObject so, PreparedStatement st) throws SQLException {
        int i = 1;
        if(so.getFirstName() != null) {
            st.setString(i++, "%" + so.getFirstName() + "%");
        }

        if(so.getLastName() != null) {
            st.setString(i++, "%" + so.getLastName() + "%");
        }

        if(so.getPatronymic() != null) {
            st.setString(i++, "%" + so.getPatronymic() + "%");
        }

        if(so.getBirthDateBigger() != null) {
            st.setDate(i++, new java.sql.Date(so.getBirthDateBigger().getTimeInMillis()));
        }

        if(so.getBirthDateLess() != null) {
            st.setDate(i++, new java.sql.Date(so.getBirthDateLess().getTimeInMillis()));
        }

        if(so.getGender() != null) {
            st.setString(i++, so.getGender().toString());
        }

        if(so.getCitizenship() != null) {
            st.setString(i++, so.getCitizenship());
        }

        if(so.getMaritalStatus() != null) {
            st.setString(i++, so.getMaritalStatus().toString());
        }

        if(so.getCountry() != null) {
            st.setString(i++, so.getCountry());
        }

        if(so.getCity() != null) {
            st.setString(i++, "%" + so.getCity() + "%");
        }

        if(so.getLocalAddress() != null) {
            st.setString(i++, "%" + so.getLocalAddress() + "%");
        }

        if(so.getIndex() != null) {
            st.setString(i++, "%" + so.getIndex() + "%");
        }
        return i;
    }

    public int count(SearchObject so, String condition) throws DaoException {
        int amount = 0;
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(COUNT + condition + ";")) {
            setParameters(so, st);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return amount;
    }

    public List<Contact> search(SearchObject so, String condition, int offset, int amount) throws DaoException {
        LinkedList<Contact> contactList = new LinkedList<Contact>();
        try (Connection cn = getConnection(); PreparedStatement st = cn.prepareStatement(SELECT + condition + LIMIT)) {
            int idx = setParameters(so, st);
            st.setInt(idx++, offset);
            st.setInt(idx++, amount);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contactList.add(ContactDao.parse(rs));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return contactList;
    }

}
